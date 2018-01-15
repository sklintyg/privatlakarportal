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
/**
 * Form directive to enter grunduppgifter
 */
angular.module('privatlakareApp').directive('formGrundUppgifter',
        function($log,
            FormGrundUppgifterViewState, messageService, ObjectHelper) {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: {
                    'user': '=',
                    'registerModel': '='
                },
                controller: function($scope) {

                    $scope.focusBefattning = true;

                    if(!ObjectHelper.isDefined($scope.registerModel) || !ObjectHelper.isDefined($scope.user)) {
                        $log.debug('formGrundUppgifter requires parameters register-model and user');
                    }

                    $scope.message = messageService;
                    $scope.viewState = FormGrundUppgifterViewState;
                },
                templateUrl: '/components/appDirectives/formGrundUppgifter/formGrundUppgifter.directive.html'
            };
        });
