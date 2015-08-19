angular.module('privatlakareApp')
    .controller('RegisterAbortCtrl', function($scope, $sessionStorage, UserModel, RegisterModel) {
        'use strict';

        $scope.dismiss = function() {
            $scope.$dismiss();
        };

        $scope.abort = function() {
            $sessionStorage.registerModel = RegisterModel.reset();
            UserModel.logout();
        };
    });
