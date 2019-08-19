angular.module('privatlakareApp').directive('ppFooter', [
  'dialogService',
  function(dialogService) {
    'use strict';

    return {
      restrict: 'E',
      scope: {},
      templateUrl: '/components/commonDirectives/ppFooter/ppFooter.directive.html',
      link: function($scope) {
        $scope.openCookieDialog = function() {
          dialogService.open({
            templateUrl: '/components/commonDirectives/ppFooter/ppFooter.cookie.modal.html'
          });
        };
      }
    };
  }]);
