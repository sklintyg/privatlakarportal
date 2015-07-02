/**
 * Form directive to enter grunduppgifter
 */
angular.module('privatlakareApp').directive('formGrundUppgifter',
    [
        function() {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: true,
                controller: function($scope) {
                },
                templateUrl: 'components/formGrundUppgifter/formGrundUppgifter.directive.html'
            };
        }]);
