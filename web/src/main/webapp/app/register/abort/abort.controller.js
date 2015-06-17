angular.module('privatlakareApp')
    .controller('RegisterAbortCtrl', function($scope, UserModel) {
        'use strict';

        $scope.dismiss = function() {
            $scope.$dismiss();
        };

        $scope.abort = function() {
            UserModel.logout();
        };
    });
