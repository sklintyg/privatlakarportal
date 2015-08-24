/**
 * Form directive to enter kontaktuppgifter
 */
angular.module('privatlakareApp').directive('formKontaktUppgifter',

        function($log, $timeout, $sessionStorage,
            RegisterViewStateService) {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: true,
                controller: function($scope) {
                    $scope.preventPaste = function(e, fieldName){
                        e.preventDefault();
                        RegisterViewStateService.errorMessage['paste'+fieldName] = true;
                        return false;
                    };
                },
                templateUrl: 'components/appDirectives/formKontaktUppgifter/formKontaktUppgifter.directive.html'
            };
        });