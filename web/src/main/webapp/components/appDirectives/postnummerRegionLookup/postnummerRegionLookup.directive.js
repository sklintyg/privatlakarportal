/**
 * Form directive to enter postnummer and receive region data
 */
angular.module('privatlakareApp').directive('postnummerRegionLookup',

        function($log, $timeout, $sessionStorage,
            RegisterViewStateService, OmradeProxy, PostnummerHelper) {
            'use strict';

            return {
                restrict: 'A',
                scope: {
                    registerForm: '=',
                    registerModel: '=',
                    viewState: '='
                },
                controller: function($scope) {

                    function setKommunSelected(value) {
                        $sessionStorage.kommunSelected = value;
                        $scope.viewState.kommunSelected = $sessionStorage.kommunSelected;
                    }

                    function clearRegionData() {
                        $scope.registerModel.postort = null;
                        $scope.registerModel.kommun = null;
                        setKommunSelected(null);
                        $scope.registerModel.lan = null;
                        $scope.viewState.kommunSelectionMode = false;
                    }

                    function setRegionModel(regionList) {
                        if (regionList.length > 1) {
                            $scope.registerModel.postort = regionList[0].postort;

                            // Reset everything if kommun hasn't been chosen
                            if ($scope.registerModel.kommun === null) {
                                $scope.registerModel.kommun = null;
                                setKommunSelected(null);
                                $scope.registerModel.lan = null;
                            } else {
                                // Otherwise set loaded values
                                setKommunSelected({ id: $scope.registerModel.kommun,
                                    label: $scope.registerModel.kommun, lan: $scope.registerModel.lan });
                            }

                        } else {
                            $scope.registerModel.postort = regionList[0].postort;
                            $scope.registerModel.kommun = regionList[0].kommun;
                            $scope.registerModel.lan = regionList[0].lan;
                        }
                    }

                    function loadRegions(postnummer) {
                        if(PostnummerHelper.isValidPostnummer(postnummer)) {
                            $scope.viewState.validPostnummer = true;
                            $scope.viewState.loading.region = true;
                            //$timeout(function() { // for testing loading animation

                            OmradeProxy.getOmradeList(postnummer).then(function(regionList) {
                                $scope.viewState.loading.region = false;
                                $scope.viewState.errorMessage.region = null;
                                if(regionList === null) {
                                    $log.debug('No region was found for postnummer:' + postnummer);
                                    clearRegionData();
                                } else {
                                    $sessionStorage.cachedRegionList = angular.copy(regionList);
                                    updateRegionView(regionList);
                                    setRegionModel(regionList);
                                }

                            }, function(errorData) {
                                $log.debug('Failed to get omradeList: ' + errorData);
                                $scope.viewState.loading.region = false;
                                $scope.viewState.errorMessage.region = 'Ett tekniskt fel har uppstått.' +
                                    ' Postnumret kunde inte hämtas. Prova igen senare.';
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
                            angular.forEach(regionList, function(value/*, key*/) {
                                this.push({ id: value.kommun, label: value.kommun, lan: value.lan});
                            }, $scope.viewState.kommunOptions);
                        } else {
                            $scope.viewState.kommunSelectionMode = false;
                        }
                    }

                    $scope.$watch('registerModel.postnummer', function(newVal/*, oldVal*/) {
                        loadRegions(newVal);
                    });

                    $scope.$watch('viewState.kommunSelected', function(newVal) {
                        if(!$scope.viewState.kommunSelectionMode){
                            return;
                        }

                        if(typeof newVal === 'object' && newVal !== null) {
                            $scope.registerModel.kommun = newVal.label;
                            setKommunSelected(newVal);
                            $scope.registerModel.lan = newVal.lan;
                        } else {
                            $scope.registerModel.kommun = null;
                            setKommunSelected(null);
                            $scope.registerModel.lan = null;
                        }
                    });
                },
                templateUrl: 'components/appDirectives/postnummerRegionLookup/postnummerRegionLookup.directive.html'
            };
        });