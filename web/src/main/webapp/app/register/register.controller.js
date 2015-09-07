angular.module('privatlakareApp')
    .controller('RegisterCtrl', function($scope, $state, UserModel, RegisterModel, RegisterViewState) {
        'use strict';
        $scope.user = UserModel.get();
        $scope.registerModel = RegisterModel.init();
        $scope.viewState = RegisterViewState;
    });
