'use strict';

angular.module('privatlakareApp')
    .controller('RegisterCtrl', function ($scope, UserModel, RegisterModel) {
        $scope.user = UserModel;
        $scope.registerModel = RegisterModel;
    });
