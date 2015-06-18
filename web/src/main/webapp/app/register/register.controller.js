angular.module('privatlakareApp')
    .controller('RegisterCtrl', function($scope, $state, UserModel, RegisterModel, RegisterViewStateService) {
        'use strict';
        $scope.user = UserModel;
        $scope.registerModel = RegisterModel.init();
        $scope.viewState = RegisterViewStateService;
    });
