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
/*                controller: function($scope, $element, $attrs) {
                    $scope.alertMessage = $attrs.alertMessage;
                },*/
                templateUrl: 'components/commonDirectives/alertMessage/alertMessage.directive.html'
            };
        }]);
