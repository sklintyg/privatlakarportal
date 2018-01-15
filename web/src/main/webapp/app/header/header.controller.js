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
angular.module('privatlakareApp').controller('HeaderController',
        function($scope, $window, $state, $log, UserModel, LinkBuilder, $sessionStorage) {
            'use strict';

            //Expose 'now' as a model property for the template to render as todays date
            $scope.today = new Date();
            $scope.userModel = UserModel;
            $scope.user = UserModel.get();
            $scope.statusText = '';

            $scope.$watch('user', function(newVal) {
                $log.debug('header controller - user status updated:');
                $log.debug(newVal.status);
                $scope.statusText = UserModel.getStatusText();
            }, true);

            $scope.$on('$stateChangeSuccess',
                function(event, toState, toParams, fromState/*, $state, fromParams*/) {
                    // sessionStorage.from is defined in boot.controller.js.
                    $scope.exitLink = LinkBuilder.getExitLink(fromState.name, toState.name, UserModel.get().status, $sessionStorage.from);
                });

            /**
             * Private functions
             */

            /**
             * Exposed scope interaction functions
             */

            $scope.logoutLocation = UserModel.getLogoutLocation();
        }
    );
