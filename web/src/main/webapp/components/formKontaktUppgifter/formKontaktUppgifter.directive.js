/**
 * Form directive to enter kontaktuppgifter
 */
angular.module('privatlakareApp').directive('formKontaktUppgifter',

        function($log, $timeout,
            RegisterViewStateService, OmradeProxy) {
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

                    function clearRegionData() {
                        $scope.registerModel.postort = null;
                        $scope.registerModel.kommun = null;
                        $scope.registerModel.lan = null;
                    }

                    $scope.$watch('registerModel.postnummer', function(newVal) {

                        if(RegisterViewStateService.isValidPostnummer(newVal)) {
                            $scope.viewState.validPostnummer = true;
                            $scope.viewState.loading.region = true;
                            $timeout(function() {

                                OmradeProxy.getOmradeList(newVal).then(function(successData) {
                                    $scope.viewState.loading.region = false;
                                    if(successData === null) {
                                        $log.debug('No region was found for postnummer:' + newVal);
                                        clearRegionData();
                                    } else {
                                        $scope.registerModel.postort = successData[0].postort;
                                        $scope.registerModel.kommun = successData[0].kommun;
                                        $scope.registerModel.lan = successData[0].lan;
                                    }

                                }, function(errorData) {
                                    $log.debug('Failed to get omradeList: ' + errorData);
                                    $scope.viewState.loading.region = false;
                                    clearRegionData();
                                });

                            }, 1000);
                        } else {
                            $scope.viewState.validPostnummer = false;
                            clearRegionData();
                        }
                    });
                },
                templateUrl: 'components/formKontaktUppgifter/formKontaktUppgifter.directive.html'
            };
        });