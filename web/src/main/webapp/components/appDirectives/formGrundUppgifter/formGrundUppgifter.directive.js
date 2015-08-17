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
                templateUrl: 'components/appDirectives/formGrundUppgifter/formGrundUppgifter.directive.html'
            };
        }]);
