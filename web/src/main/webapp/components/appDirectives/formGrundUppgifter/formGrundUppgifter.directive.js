/**
 * Form directive to enter grunduppgifter
 */
angular.module('privatlakareApp').directive('formGrundUppgifter',
        function(FormGrundUppgifterViewState, messageService) {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: true,
                controller: function($scope) {
                    $scope.message = messageService;
                    $scope.viewState = FormGrundUppgifterViewState;
                },
                templateUrl: 'components/appDirectives/formGrundUppgifter/formGrundUppgifter.directive.html'
            };
        });
