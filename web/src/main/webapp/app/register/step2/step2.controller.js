angular.module('privatlakareApp')
    .controller('Step2Ctrl', function($scope, $timeout, $state, RegisterViewStateService) {
        'use strict';

        // function to submit the form after all validation has occurred
        $scope.submitForm = function() {
            $state.go('app.register.step3');
        };

        $scope.preventPaste = function(e, fieldName){
            e.preventDefault();
            RegisterViewStateService['pasteError'+fieldName] = true;
/*            $timeout(function() {
                RegisterViewStateService['pasteError'+fieldName] = false;
            }, 3000);*/
            return false;
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
