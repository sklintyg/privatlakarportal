angular.module('privatlakareApp')
    .controller('MinsidaCtrl', function($scope, $state, $log,
        UserModel, RegisterModel, RegisterViewStateService, RegisterProxy, ObjectHelper) {
        'use strict';
        $scope.user = UserModel;
        $scope.registerModel = RegisterModel.reset();
        $scope.viewState = RegisterViewStateService.reset();

        RegisterProxy.getPrivatlakare().then(function(lakarData) {
            $log.debug('MinsidaCtrl - Got privatlakaredata:');
            $log.debug(lakarData);

            if(ObjectHelper.isDefined(lakarData)) {
                RegisterModel.set(lakarData);
                $scope.registerModel = RegisterModel.get();
            } else {
                $scope.registerModel = RegisterModel.reset();
                $scope.viewState = RegisterViewStateService.reset();
            }
        }, function(errorData) {
            $log.debug('MinsidaCtrl - Got error:');
            $log.debug(errorData);
        });
    });
