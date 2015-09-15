angular.module('privatlakareApp')
  .controller('Step2Ctrl', function ($scope, $state, $window,
        RegisterModel, RegisterViewState, WindowUnload, UserModel) {
        'use strict';

        $scope.focusTelefonnummer = true;

        if(UserModel.isRegistered()) {
            $state.go('app.register.complete');
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
            function(event, toState/*, toParams, fromState, fromParams*/) {
                if (!RegisterViewState.navigationAllowed(toState, $scope.registerForm.$valid)) {
                    event.preventDefault();
                    // transitionTo() promise will be rejected with
                    // a 'transition prevented' error
                }
            });

        $scope.$on('$stateChangeSuccess',
            function(/*event, toState, toParams, fromState, fromParams*/) {
                RegisterViewState.updateStep();
            });

        // Add browser dialog to ask if user wants to save before leaving if he closes the window on an edited form.
        WindowUnload.bindUnload($scope, RegisterViewState.windowUnloadWarningCondition);
    });
