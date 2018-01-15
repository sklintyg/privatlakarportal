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
 * Adds validation to postnummer region to field.
 */
angular.module('privatlakareApp').directive('validateRegion',
    function(PostnummerHelper, ObjectHelper) {
        'use strict';

        return {
            restrict: 'A',
            require: 'ngModel',
            scope: {
                regionModel: '='
            },
            link: function(scope, elem, attrs, ctrl){

                // Validate kommun and lan
                scope.$watch('regionModel', function(newVal) {
                    if(!ObjectHelper.isDefined(newVal) || !ObjectHelper.isDefined(newVal.kommun) || !ObjectHelper.isDefined(newVal.lan)) {
                        ctrl.$setValidity('region', false);
                    } else {
                        ctrl.$setValidity('region', true);
                    }
                }, true);

                // Validate postnummer format
                var validator = function(value){
                    ctrl.$setValidity('format', PostnummerHelper.isValidPostnummer(value));
                    return value;
                };

                ctrl.$parsers.unshift(validator);
                ctrl.$formatters.unshift(validator);
            }
        };
    });