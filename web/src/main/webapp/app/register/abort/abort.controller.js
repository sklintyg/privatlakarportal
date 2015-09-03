angular.module('privatlakareApp')
    .controller('RegisterAbortCtrl', function($scope, $sessionStorage, UserModel, RegisterModel, RegisterViewState) {
        'use strict';

        $scope.dismiss = function() {
            RegisterViewState.windowUnloadWarningCondition.condition = true;
            $scope.$dismiss();
        };

        $scope.abort = function() {
            RegisterViewState.windowUnloadWarningCondition.condition = false;
            $sessionStorage.registerModel = RegisterModel.reset();
            UserModel.logout();
        };
    });
