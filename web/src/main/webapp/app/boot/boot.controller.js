/*
 * Copyright (C) 2018 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/* globals location */
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
            if (location.search && location.search.length > 1) {
                angular.forEach(location.search.substring(1).split('&'), function (param) {
                    var array = param.split('=');
                    if (array[0] === 'from') {
                        $sessionStorage.from = decodeURIComponent(array[1]);
                    }
                });
            }
            $log.debug('Boot controller: $sessionStorage.from');
            $log.debug( $sessionStorage.from);
            $state.go('app.minsida');
            break;
        default:
        }

    });
