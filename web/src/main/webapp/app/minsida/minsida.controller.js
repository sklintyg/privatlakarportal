angular.module('privatlakareApp')
    .controller('MinsidaCtrl', function($scope, $state, $log, $window,
        UserModel, RegisterModel, RegisterViewState, RegisterProxy, ObjectHelper, WindowUnload) {
        'use strict';
        $scope.user = UserModel.init();
        $scope.registerModel = RegisterModel.reset();
        $scope.viewState = RegisterViewState.reset();

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
                $scope.viewState = RegisterViewState.reset();
            }
        }

        $scope.$watch('user', function(/*newVal*/) {
            RegisterViewState.errorMessage.noPermission = !UserModel.hasApplicationPermission();
        }, true);

        RegisterProxy.getPrivatlakare().then(function(lakarData) {
            $log.debug('MinsidaCtrl - Got privatlakaredata:');
            $log.debug(lakarData);
            updateState(lakarData);
        }, function(errorData) {
            $log.debug('MinsidaCtrl - Got error:');
            $log.debug(errorData);
        });

        $scope.save = function() {
            RegisterViewState.loading.save = true;
            RegisterProxy.savePrivatlakare(RegisterModel.get()).then(function(/*successData*/) {
                RegisterViewState.loading.save = false;
                RegisterViewState.errorMessage.save = null;
                $scope.registerForm.$setPristine();
            }, function(errorData) {
                RegisterViewState.loading.save = false;
                RegisterViewState.errorMessage.save = 'Kunde inte spara ändringarna. Försök igen senare. (' + errorData.message + ')';
            });
        };

        // Add browser dialog to ask if user wants to save before leaving if he closes the window on an edited form.
        $scope.$watch('registerForm.$dirty',  function(newVal) {
            RegisterViewState.windowUnloadWarningCondition.condition = newVal;
        });
        WindowUnload.bindUnload($scope, RegisterViewState.windowUnloadWarningCondition);
    });
