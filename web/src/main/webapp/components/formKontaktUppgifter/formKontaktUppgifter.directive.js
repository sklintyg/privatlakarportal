/**
 * Form directive to enter kontaktuppgifter
 */
angular.module('privatlakareApp').directive('formKontaktUppgifter',

        function(RegisterViewStateService) {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: true,
                controller: function($scope) {
                    $scope.preventPaste = function(e, fieldName){
                        e.preventDefault();
                        RegisterViewStateService.errorMessage['paste'+fieldName] = true;
                        /*            $timeout(function() {
                         RegisterViewStateService['pasteError'+fieldName] = false;
                         }, 3000);*/
                        return false;
                    };
                },
                templateUrl: 'components/formKontaktUppgifter/formKontaktUppgifter.directive.html'
            };
        });