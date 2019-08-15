/**
 * Show an alert box
 */
angular.module('privatlakareApp').directive('alertMessage',
    [
      function() {
        'use strict';

        return {
          restrict: 'A',
          scope: {
            'alertShow': '=',
            'alertMessageId': '@',
            'alertSeverity': '@'
          },
          controller: function($scope) {
            if ($scope.alertShow === undefined) {
              $scope.alertShow = true;
            }
          },
          templateUrl: '/components/commonDirectives/alertMessage/alertMessage.directive.html'
        };
      }]);
