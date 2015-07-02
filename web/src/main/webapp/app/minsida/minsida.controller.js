'use strict';

angular.module('privatlakareApp')
  .controller('MinsidaCtrl', function ($scope, $state, UserModel, RegisterModel, RegisterViewStateService) {
        $scope.user = UserModel;
        $scope.registerModel = RegisterModel.init();
        $scope.viewState = RegisterViewStateService;
  });
