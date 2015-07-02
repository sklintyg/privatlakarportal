'use strict';

angular.module('privatlakareApp')
    .controller('Step1Ctrl', function($scope, $state, RegisterViewStateService) {
        // function to submit the form after all validation has occurred
        $scope.submitForm = function() {
            $state.go('app.register.step2');
        };

        $scope.$on('$stateChangeStart',
            function(event, toState/*, toParams, fromState, fromParams*/) {

                if (!RegisterViewStateService.navigationAllowed(toState, $scope.registerForm.$valid)) {
                    event.preventDefault();
                    // transitionTo() promise will be rejected with
                    // a 'transition prevented' error
                }
            });

        $scope.$on('$stateChangeSuccess',
            function(/*event, toState, toParams, fromState, fromParams*/) {
                RegisterViewStateService.updateStep();
            });

    });
