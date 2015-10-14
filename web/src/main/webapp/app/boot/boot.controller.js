angular.module('privatlakareApp')
    .controller('BootCtrl', function($scope, $timeout, $state, $stateParams, $window, $sessionStorage, $log, UserModel) {
        'use strict';

        switch (UserModel.get().status) {
        case 'NOT_STARTED':
            if (UserModel.get().nameFromPuService) {
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
            // Add from to $sessionStorage in case the user reloads the page and we loose url parameters.
            $log.debug('Boot controller: $stateParams.from');
            $log.debug($stateParams.from);
            $sessionStorage.from = $stateParams.from;
            $state.go('app.minsida');
            break;
        default:
        }

    });
