/**
 * Show an alert that can be closed
 */
angular.module('privatlakareApp').directive('alertMessage',
    [
        function() {
            'use strict';

            return {
                restrict: 'A',
                scope: {
                    'alertModel' : '=',
                    'alertSeverity' : '@'
                },
                templateUrl: 'components/commonDirectives/alertMessage/alertMessage.directive.html'
            };
        }]);
