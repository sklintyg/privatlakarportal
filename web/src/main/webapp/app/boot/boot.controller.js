angular.module('privatlakareApp')
    .controller('BootCtrl', function($scope, $timeout, $state, $window,
        UserProxy, UserModel) {
        'use strict';

        $scope.user = UserModel.get();

        $scope.$watch('user', function(newVal) {
            switch (newVal.status) {
            case 'NOT_STARTED':
                $state.go('app.start');
                break;
            case 'NOT_AUTHORIZED':
            case 'WAITING_FOR_HOSP':
            case 'AUTHORIZED':
                $state.go('app.minsida');
                break;
            default:
            }
        });

    });
