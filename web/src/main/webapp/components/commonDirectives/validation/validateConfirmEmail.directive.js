/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
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
 * Adds validation to confirm values are identical between two fields.
 */
angular.module('privatlakareApp').directive('validateConfirmEmail',
    [
      function() {
        'use strict';

        return {
          restrict: 'A',
          require: ['^form', 'ngModel'],
          scope: {
            validateConfirmEmail: '='
          },
          link: function(scope, elem, attrs, ctrl) {
            var form = ctrl[0];
            var model = ctrl[1];
            scope.epostCtrl = form.epost;
            scope.epost2Ctrl = model;
            scope.$watch('[epostCtrl.$viewValue, epost2Ctrl.$viewValue]', function(newVal) {
              if (newVal[0] && newVal[1]) {
                ctrl[1].$setValidity('confirmEmail', newVal[0] === newVal[1]);
              }
            });
          }
        };
      }]);
