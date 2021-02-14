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

angular.module('privatlakareApp')
.controller('MinsidaCtrl', function($scope, $state, $log, $window, $timeout,
    HospModel, HospService, HospViewState, MinsidaViewState, ObjectHelper, RegisterModel, RegisterProxy,
    UserModel, WindowUnload, $sessionStorage) {
  'use strict';

  /**
   * Initialization
   */
  $scope.user = UserModel.get();
  $scope.registerModel = RegisterModel.reset();
  $scope.viewState = MinsidaViewState.reset();

  $scope.$on('$stateChangeSuccess', function(ev, to, toParams, from) {
    if (from.name === 'app.minsida.terms') {
      if ($sessionStorage.appTermsModalModel && $sessionStorage.appTermsModalModel.options) {
        $sessionStorage.appTermsModalModel.options.modalInstance.close();
      }
    }
  });

  /**
   * Supporting functions
   */
  function updateState(lakarData) {
    if (ObjectHelper.isDefined(lakarData)) {
      RegisterModel.set(lakarData.registration);
      $scope.registerModel = RegisterModel.get();
      MinsidaViewState.setUndoModel($scope.registerModel);
      HospModel.init();
      HospService.updateHosp('update', HospViewState, HospModel, lakarData.hospInformation);
    } else {
      $scope.registerModel = RegisterModel.reset();
      $scope.viewState = MinsidaViewState.reset();
    }
  }

  /**
   * Watches
   */
  $scope.$watch('user', function(/*newVal*/) {
    MinsidaViewState.errorMessage.noPermission = !UserModel.hasApplicationPermission();
  }, true);

  RegisterProxy.getPrivatlakare().then(function(lakarData) {
    $log.debug('MinsidaCtrl - Got privatlakaredata:');
    $log.debug(lakarData);
    updateState(lakarData);
  }, function(errorData) {
    $log.debug('MinsidaCtrl - Got error:');
    $log.debug(errorData);
    MinsidaViewState.errorMessage.load = 'Kunde inte ladda formuläret. Prova ladda om sidan. (' + errorData.message + ')';
  });

  $scope.$watch('registerModel', function() {
    MinsidaViewState.checkFieldsRequiringLogout($scope.registerForm);
  }, true);

  // Add browser dialog to ask if user wants to save before leaving if he closes the window on an edited form.
  $scope.$watch('registerForm.$dirty', function(newVal) {
    if (newVal) {
      WindowUnload.enable();
    } else {
      WindowUnload.disable();
    }
  });
  WindowUnload.bindUnload($scope);

  /**
   * Interactions
   */
  $scope.save = function() {

    if ($scope.registerForm.$invalid) {
      MinsidaViewState.errorMessage.save = 'Kunde inte spara ändringarna. Det finns fel i formuläret.';
      return;
    }

    MinsidaViewState.loading.save = true;
    RegisterProxy.savePrivatlakare(RegisterModel.get()).then(function(/*successData*/) {
      MinsidaViewState.loading.save = false;
      MinsidaViewState.errorMessage.save = null;
      $scope.registerForm.$setPristine();
      if (MinsidaViewState.changesRequireLogout()) {
        UserModel.logout();
      }
    }, function(errorData) {
      MinsidaViewState.loading.save = false;
      MinsidaViewState.errorMessage.save = 'Kunde inte spara ändringarna. Försök igen senare. (' + errorData.message + ')';
    });
  };

  $scope.reset = function() {
    $scope.registerModel = MinsidaViewState.resetModelFromUndoModel(RegisterModel, $scope.registerForm);
  };
});
