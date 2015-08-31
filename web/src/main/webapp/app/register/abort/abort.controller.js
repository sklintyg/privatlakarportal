angular.module('privatlakareApp')
    .controller('RegisterAbortCtrl', function($scope, $sessionStorage, UserModel, RegisterModel, RegisterViewStateService) {
        'use strict';

        $scope.dismiss = function() {
            RegisterViewStateService.windowUnloadWarningCondition.condition = true;
            $scope.$dismiss();
        };

        $scope.abort = function() {
            RegisterViewStateService.windowUnloadWarningCondition.condition = false;
            $sessionStorage.registerModel = RegisterModel.reset();
            UserModel.logout();
        };
    });
