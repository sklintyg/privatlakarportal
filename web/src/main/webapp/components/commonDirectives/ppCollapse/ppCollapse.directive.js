
/**
 * Enable tooltips for other components than wcFields
 */
angular.module('privatlakareApp').directive('ppCollapse',
    [ 'messageService',
        function() {
            'use strict';

            return {
                restrict: 'E',
                transclude: true,
                scope: {
                    label: '@'
                },
                link: function($scope) {
                    $scope.isCollapsed = true;
                },
                templateUrl: '/components/commonDirectives/ppCollapse/ppCollapse.directive.html'
            };
        }]);
