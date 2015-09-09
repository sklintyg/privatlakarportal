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
                    'alertShow' : '=',
                    'alertMessageId' : '@',
                    'alertSeverity' : '@'
                },
                templateUrl: 'components/commonDirectives/alertMessage/alertMessage.directive.html'
            };
        }]);
