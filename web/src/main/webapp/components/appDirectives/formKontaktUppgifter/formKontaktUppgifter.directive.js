/**
 * Form directive to enter kontaktuppgifter
 */
angular.module('privatlakareApp').directive('formKontaktUppgifter',

        function($log, $timeout, $sessionStorage,
            RegisterViewState) {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: true,
                controller: function($scope) {
                    $scope.preventPaste = function(e, fieldName){
                        e.preventDefault();
                        RegisterViewState.errorMessage['paste'+fieldName] = true;
                        return false;
                    };
                },
                templateUrl: 'components/appDirectives/formKontaktUppgifter/formKontaktUppgifter.directive.html'
            };
        });