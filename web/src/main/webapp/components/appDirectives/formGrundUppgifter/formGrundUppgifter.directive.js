/**
 * Form directive to enter grunduppgifter
 */
angular.module('privatlakareApp').directive('formGrundUppgifter',
        function(messageService) {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: true,
                controller: function($scope) {
                    $scope.message = messageService;
                },
                templateUrl: 'components/appDirectives/formGrundUppgifter/formGrundUppgifter.directive.html'
            };
        });
