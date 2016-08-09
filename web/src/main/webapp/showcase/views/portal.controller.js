/*
 * Copyright (C) 2016 Inera AB (http://www.inera.se)
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

angular.module('showcase').controller('showcase.PortalCtrl',
    ['$scope', '$window', 'dialogService',
        function($scope, $window, dialogService) {
            'use strict';

            $scope.show = true;
            $scope.text = 'Text';
            $scope.showSingle = true;
            $scope.loading = true;
            $scope.registerForm = {};
            $scope.registerModel = {};

            $scope.showCookieBanner = false;
            $scope.doShowCookieBanner = function() {
                $window.localStorage.setItem('pp-cookie-consent-given', '0');
                $scope.showCookieBanner = !$scope.showCookieBanner;
            };

            $scope.openModal = function() {
                dialogService.open({
                    templateUrl: '/showcase/views/modal.html',
                    controller: ['$scope', function($scope) {
                        $scope.dismiss = function() {
                            $scope.$dismiss();
                        };
                    }]
                });
            };

        }]);
