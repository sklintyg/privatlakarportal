/**
 * Show an alert that can be closed
 */
angular.module('privatlakareApp').directive('alertSingle',
    [ 'messageService',
        function(messageService) {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: {
                    'alertModel' : '=',
                    'alertMessageId' : '@'
                },
                controller: function($scope) {
                    $scope.close = function() {
                        $scope.alertModel = false;
                    };
                },
                templateUrl: 'components/alertSingle/alertSingle.directive.html'
            };
        }]);
