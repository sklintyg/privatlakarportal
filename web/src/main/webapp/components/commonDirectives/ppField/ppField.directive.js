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
 * Enable tooltips for other components than wcFields
 */
angular.module('privatlakareApp').directive('ppField',
    ['messageService',
      function(messageService) {
        'use strict';

        return {
          require: '^form',
          restrict: 'A',
          transclude: true,
          scope: {},
          link: function($scope, $element, $attrs, FormController) {

            $scope.registerForm = FormController;

            $scope.controlName = $attrs.ppField;
            $scope.showHelp = messageService.propertyExists('label.form.' + $scope.controlName + '.help');
            $scope.isValid = function() {
              if (!$scope.registerForm[$scope.controlName]) {
                return true;
              }
              return !($scope.registerForm[$scope.controlName].$invalid && $scope.registerForm.$submitted);
            };
          },
          templateUrl: '/components/commonDirectives/ppField/ppField.directive.html'
        };
      }]);
