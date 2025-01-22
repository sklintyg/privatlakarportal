/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

angular.module('privatlakareApp')
.controller('Step3Ctrl',
    function($scope, $log, $state, $window, UserModel, RegisterModel, RegisterProxy, Step3ViewState,
        TermsProxy, WindowUnload) {
      'use strict';

      var user = UserModel.get();
      if (UserModel.isRegistered()) {
        $state.go('app.register.complete');
        return;
      }

      if (!RegisterModel.validForStep3()) {
        $state.go('app.register.step2');
        return;
      }

      var model = RegisterModel.init();
      var privatLakareDetails = Step3ViewState.getRegisterDetailsTableDataFromModel(user, model);
      $scope.uppgifter = privatLakareDetails.uppgifter;
      $scope.kontaktUppgifter = privatLakareDetails.kontaktUppgifter;
      $scope.viewState = Step3ViewState;
      $scope.registerModel = model;

      // Skapa konto button
      $scope.createAccount = function() {
        Step3ViewState.loading.register = true;

        var godkantMedgivandeVersion = null;
        if (Step3ViewState.godkannvillkor) {
          godkantMedgivandeVersion = $scope.terms.version;
        }

        RegisterProxy.registerPrivatlakare(model, godkantMedgivandeVersion).then(function(successData) {
          $log.debug('Registration complete - data:');
          $log.debug(successData);
          Step3ViewState.loading.register = false;
          Step3ViewState.errorMessage.register = null;
          user.status = successData.status;
          RegisterModel.reset();

          switch (user.status) {
          case 'AUTHORIZED':
            $state.go('app.register.complete');
            break;
          case 'NOT_AUTHORIZED':
          case 'WAITING_FOR_HOSP':
            $state.go('app.register.waiting');
            break;
          default: // NOT_STARTED, UNKNOWN or other unwanted values like null or undefined
            Step3ViewState.errorMessage.register =
                'Kunde inte registrera privatläkare på grund av tekniskt fel. Försök igen senare.';
            $log.debug('Invalid user status in response:' + user.status);
          }

        }, function(errorData) {
          Step3ViewState.loading.register = false;
          Step3ViewState.errorMessage.register =
              'Kunde inte registrera privatläkare på grund av tekniskt fel. Försök igen senare.';
          $log.debug('Failed to register errorCode:' + errorData.errorCode + ' reason:' + errorData.message);
        });
      };

      // Retrieve terms
      TermsProxy.getTerms().then(function(successData) {
        $scope.terms = {
          text: successData.text,
          date: successData.date,
          version: successData.version
        };
      }, function(errorData) {
        Step3ViewState.loading.register = false;
        Step3ViewState.errorMessage.register =
            'Kunde inte registrera privatläkare på grund av tekniskt fel. Försök igen senare.';
        $log.debug('Failed to get terms.');
        $log.debug(errorData);
      });

      // Add browser dialog to ask if user wants to save before leaving if he closes the window on an edited form.
      WindowUnload.bindUnload($scope);
    });
