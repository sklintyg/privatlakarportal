angular.module('privatlakareApp')
    .controller('Step1Ctrl', function($scope, $state, $window,
        RegisterViewState, WindowUnload, UserModel) {
        'use strict';

        if(UserModel.isRegistered()) {
            $state.go('app.register.complete');
        }

        // function to submit the form after all validation has occurred
        $scope.submitForm = function() {
            $state.go('app.register.step2');
        };

        $scope.$on('$stateChangeStart',
            function(event, toState/*, toParams, fromState, fromParams*/) {
                var navigationAllowed = RegisterViewState.navigationAllowed(toState, $scope.registerForm.$valid);
                if (!navigationAllowed) { // navigationAllowed can be null if we
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
