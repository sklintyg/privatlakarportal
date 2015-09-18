/**
 * Form directive to enter kontaktuppgifter
 */
angular.module('privatlakareApp').directive('formKontaktUppgifter',

        function($log, $timeout, $sessionStorage,
            RegisterViewState, ObjectHelper, FormKontaktUppgifterViewState) {
            'use strict';

            return {
                restrict: 'A',
                transclude: true,
                scope: {
                    'registerModel': '=',
                    'viewState': '='
                },
                controller: function($scope) {

                    if(!ObjectHelper.isDefined($scope.registerModel) || !ObjectHelper.isDefined($scope.viewState)) {
                        $log.debug('formKontaktUppgifter requires parameters register-model and view-state');
                    }
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