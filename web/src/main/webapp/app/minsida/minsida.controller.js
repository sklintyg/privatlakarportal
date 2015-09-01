angular.module('privatlakareApp')
    .controller('MinsidaCtrl', function($scope, $state, $log, $window,
        UserModel, RegisterModel, RegisterViewStateService, RegisterProxy, ObjectHelper, WindowUnload) {
        'use strict';
        $scope.user = UserModel.init();
        $scope.registerModel = RegisterModel.reset();
        $scope.viewState = RegisterViewStateService.reset();

        function updateState(lakarData) {
            if (ObjectHelper.isDefined(lakarData)) {
                RegisterModel.set(lakarData.registration);
                $scope.registerModel = RegisterModel.get();

                if(ObjectHelper.isDefined(lakarData.hospInformation)) {
                    $scope.viewState.legitimeradYrkesgrupp =
                        ObjectHelper.returnJoinedArrayOrNull(lakarData.hospInformation.hsaTitles);
                    $scope.viewState.specialitet =
                        ObjectHelper.returnJoinedArrayOrNull(lakarData.hospInformation.specialityNames);
                    $scope.viewState.forskrivarkod =
                        ObjectHelper.valueOrNull(lakarData.hospInformation.personalPrescriptionCode);
                }
            } else {
                $scope.registerModel = RegisterModel.reset();
                $scope.viewState = RegisterViewStateService.reset();
            }
        }

        RegisterProxy.getPrivatlakare().then(function(lakarData) {
            $log.debug('MinsidaCtrl - Got privatlakaredata:');
            $log.debug(lakarData);
            updateState(lakarData);
        }, function(errorData) {
            $log.debug('MinsidaCtrl - Got error:');
            $log.debug(errorData);
        });

        $scope.save = function() {
            RegisterViewStateService.loading.save = true;
            RegisterProxy.savePrivatlakare(RegisterModel.get()).then(function(/*successData*/) {
                RegisterViewStateService.loading.save = false;
                RegisterViewStateService.errorMessage.save = null;
                $scope.registerForm.$setPristine();
            }, function(errorData) {
                RegisterViewStateService.loading.save = false;
                RegisterViewStateService.errorMessage.save = 'Kunde inte spara ändringarna. Försök igen senare. (' + errorData.message + ')';
            });
        };

        // Add browser dialog to ask if user wants to save before leaving if he closes the window on an edited form.
        $scope.$watch('registerForm.$dirty',  function(newVal) {
            RegisterViewStateService.windowUnloadWarningCondition.condition = newVal;
        });
        WindowUnload.bindUnload($scope, RegisterViewStateService.windowUnloadWarningCondition);
    });
