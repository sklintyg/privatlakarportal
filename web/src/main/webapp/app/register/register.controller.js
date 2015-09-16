angular.module('privatlakareApp')
    .controller('RegisterCtrl', function($scope, $state, UserModel, RegisterModel) {
        'use strict';
        $scope.user = UserModel.get();
        $scope.registerModel = RegisterModel.init();
    });
