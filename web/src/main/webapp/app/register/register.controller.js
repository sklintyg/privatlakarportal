angular.module('privatlakareApp')
    .controller('RegisterCtrl', function($scope, $state, UserModel, RegisterModel, RegisterViewState) {
        'use strict';
        $scope.user = UserModel.init();
        $scope.registerModel = RegisterModel.init();
        $scope.viewState = RegisterViewState;
    });
