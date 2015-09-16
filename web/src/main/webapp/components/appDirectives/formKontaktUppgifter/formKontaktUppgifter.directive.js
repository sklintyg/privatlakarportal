/**
 * Form directive to enter kontaktuppgifter
 */
angular.module('privatlakareApp').directive('formKontaktUppgifter',

        function($log, $timeout, $sessionStorage,
            FormKontaktUppgifterViewState) {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: true,
                controller: function($scope) {
                    $scope.viewState = FormKontaktUppgifterViewState;
                    $scope.preventPaste = function(e, fieldName){
                        e.preventDefault();
                        FormKontaktUppgifterViewState.errorMessage['paste'+fieldName] = true;
                        return false;
                    };
                },
                templateUrl: 'components/appDirectives/formKontaktUppgifter/formKontaktUppgifter.directive.html'
            };
        });