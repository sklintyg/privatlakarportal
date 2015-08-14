/**
 * Form directive to enter kontaktuppgifter
 */
angular.module('privatlakareApp').directive('formKontaktUppgifter',

        function($log, $timeout, $sessionStorage,
            RegisterViewStateService, OmradeProxy, PostnummerHelper) {
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

                    function clearRegionData() {
                        $scope.registerModel.postort = null;
                        $scope.registerModel.kommun = null;
                        $sessionStorage.kommunSelected = null;
                        $scope.viewState.kommunSelected = $sessionStorage.kommunSelected;
                        $scope.registerModel.lan = null;
                        $scope.viewState.kommunSelectionMode = false;
                    }

                    function loadRegions(postnummer) {
                        if(PostnummerHelper.isValidPostnummer(postnummer)) {
                            $scope.viewState.validPostnummer = true;
                            $scope.viewState.loading.region = true;
                            //$timeout(function() { // for testing loading animation

                            OmradeProxy.getOmradeList(postnummer).then(function(regionList) {
                                $scope.viewState.loading.region = false;
                                if(regionList === null) {
                                    $log.debug('No region was found for postnummer:' + postnummer);
                                    clearRegionData();
                                } else {
                                    $sessionStorage.cachedRegionList = angular.copy(regionList);
                                    updateRegionView(regionList);
                                    if(regionList.length > 1) {
                                        $scope.registerModel.postort = regionList[0].postort;
                                        $scope.registerModel.kommun = null;
                                        $sessionStorage.kommunSelected = null;
                                        $scope.registerModel.lan = null;
                                    } else {
                                        $scope.registerModel.postort = regionList[0].postort;
                                        $scope.registerModel.kommun = regionList[0].kommun;
                                        $scope.registerModel.lan = regionList[0].lan;
                                    }
                                }

                            }, function(errorData) {
                                $log.debug('Failed to get omradeList: ' + errorData);
                                $scope.viewState.loading.region = false;
                                clearRegionData();
                            });

                            //}, 1000);
                        } else {
                            $scope.viewState.validPostnummer = false;
                            clearRegionData();
                        }
                    }

                    function updateRegionView(regionList) {
                        $scope.viewState.kommunHits = regionList.length;
                        $scope.viewState.kommunOptions = [];
                        if(regionList.length > 1) {
                            $scope.viewState.kommunSelectionMode = true;
                            angular.forEach(regionList, function(value, key) {
                                this.push({ id: key, label: value.kommun, lan: value.lan});
                            }, $scope.viewState.kommunOptions);
                        } else {
                            $scope.viewState.kommunSelectionMode = false;
                        }
                    }

                    $scope.$watch('registerModel.postnummer', function(newVal, oldVal) {

                        if(newVal === oldVal) {
                            // First load. If we don't have a postort yet, load data from backend
                            if($scope.registerModel.postort === null) {
                                loadRegions(newVal);
                            } else { // Otherwise just set what we have in the model and only load later if the postnummer changes
                                // Update view from the last region request
                                $scope.viewState.validPostnummer = true; // Must be a valid postnummer since we have looked up postort before.
                                updateRegionView($sessionStorage.cachedRegionList);
                                $scope.viewState.kommunSelected = $sessionStorage.kommunSelected;
                            }
                        } else {
                            loadRegions(newVal);
                        }
                    });

                    $scope.$watch('viewState.kommunSelected', function(newVal) {
                        if(!$scope.viewState.kommunSelectionMode){
                            return;
                        }

                        if(typeof newVal === 'object' && newVal !== null) {
                            $scope.registerModel.kommun = newVal.label;
                            $sessionStorage.kommunSelected = newVal;
                            $scope.viewState.kommunSelected = $sessionStorage.kommunSelected;
                            $scope.registerModel.lan = newVal.lan;
                        } else {
                            $scope.registerModel.kommun = null;
                            $sessionStorage.kommunSelected = null;
                            $scope.viewState.kommunSelected = $sessionStorage.kommunSelected;
                            $scope.registerModel.lan = null;
                        }
                    });
                },
                templateUrl: 'components/formKontaktUppgifter/formKontaktUppgifter.directive.html'
            };
        });