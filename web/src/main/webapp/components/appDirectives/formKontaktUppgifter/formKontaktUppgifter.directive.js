/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
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

/**
 * Form directive to enter kontaktuppgifter
 */
angular.module('privatlakareApp').directive('formKontaktUppgifter',

    function($log, $timeout, $sessionStorage, $state,
        ObjectHelper, FormKontaktUppgifterViewState) {
      'use strict';

      return {
        restrict: 'A',
        transclude: true,
        scope: {
          'registerModel': '='
        },
        controller: function($scope) {

          if (!ObjectHelper.isDefined($scope.registerModel) || !ObjectHelper.isDefined($scope.viewState)) {
            $log.debug('formKontaktUppgifter requires parameters register-model and view-state');
          }

          if ($state.current.name !== 'app.minsida') {
            $scope.focusTelefonnummer = true;
          }
          $scope.viewState = FormKontaktUppgifterViewState;
          $scope.preventPaste = function(e, fieldName) {
            e.preventDefault();
            FormKontaktUppgifterViewState.errorMessage['paste' + fieldName] = true;
            return false;
          };
        },
        templateUrl: '/components/appDirectives/formKontaktUppgifter/formKontaktUppgifter.directive.html'
      };
    });