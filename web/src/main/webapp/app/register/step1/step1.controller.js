angular.module('privatlakareApp')
    .controller('Step1Ctrl', function($scope, $state, RegisterViewStateService, RegisterModel) {
        'use strict';
        RegisterViewStateService.updateStep();

        // function to submit the form after all validation has occurred
        $scope.submitForm = function() {

            // check to make sure the form is completely valid
            if ($scope.registerForm.$valid) {
                $state.go('app.register.step2');
                return true;
            }

            return false;
        };
    });
