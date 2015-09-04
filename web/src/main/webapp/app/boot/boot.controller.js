angular.module('privatlakareApp')
    .controller('BootCtrl', function($scope, $timeout, $state, $window, UserProxy, UserModel) {
        'use strict';

        $scope.user = UserModel.init();

        $scope.$watch('user', function(newVal) {
            switch (newVal.status) {
            case 'NOT_STARTED':
                if (newVal.nameFromPuService) {
                    $state.go('app.start');
                }
                else {
                    $state.go('app.error', {
                        errorMessage: 'Ett tekniskt fel har tyvärr uppstått och det går inte att hämta dina ' +
                            'namnuppgifter från folkbokföringsregistret för tillfället. Du kan därför inte skapa ett ' +
                            'konto för Webcert just nu. Prova igen om en stund.'
                    });
                }
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
