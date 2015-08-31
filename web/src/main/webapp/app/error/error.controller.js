angular.module('privatlakareApp')
    .controller('ErrorCtrl', function ($scope, $sessionStorage, $stateParams) {
        'use strict';
        if ($stateParams.errorMessage) {
            $sessionStorage.errorMessage = $scope.errorMessage = $stateParams.errorMessage;
        }
        else {
            $scope.errorMessage = $sessionStorage.errorMessage;
        }
    });
