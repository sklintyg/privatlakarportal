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
.controller('Step2Ctrl', function($scope, $state, $window,
    RegisterModel, RegisterNavigationService, UserModel, WindowUnload) {
  'use strict';

  if (UserModel.isRegistered()) {
    $state.go('app.register.complete');
    return;
  }

  if (!RegisterModel.validForStep2()) {
    $state.go('app.register.step1');
    return;
  }

  // function to submit the form after all validation has occurred
  $scope.submitForm = function() {
    $state.go('app.register.step3');
  };

  $scope.$on('$stateChangeStart',
      function(event, toState, toParams, fromState) {
        if (!RegisterNavigationService.navigationAllowed(toState, fromState, $scope.registerForm.$valid)) {
          event.preventDefault();
          // transitionTo() promise will be rejected with
          // a 'transition prevented' error
        }
      });

  // Add browser dialog to ask if user wants to save before leaving if he closes the window on an edited form.
  WindowUnload.bindUnload($scope);
});
