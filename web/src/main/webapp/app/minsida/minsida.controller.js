angular.module('privatlakareApp')
    .controller('MinsidaCtrl', function($scope, $state, $log,
        UserModel, RegisterModel, RegisterViewStateService, RegisterProxy, ObjectHelper) {
        'use strict';
        $scope.user = UserModel;
        $scope.registerModel = RegisterModel.reset();
        $scope.viewState = RegisterViewStateService.reset();

        function updateState(lakarData) {
            if (ObjectHelper.isDefined(lakarData)) {
                RegisterModel.set(lakarData.registration);
                $scope.registerModel = RegisterModel.get();

                $scope.viewState.legitimeradYrkesgrupp =
                    ObjectHelper.returnJoinedArrayOrNull(lakarData.hospInformation.hsaTitles);
                $scope.viewState.specialitet =
                    ObjectHelper.returnJoinedArrayOrNull(lakarData.hospInformation.specialityNames);
                $scope.viewState.forskrivarkod =
                    ObjectHelper.valueOrNull(lakarData.hospInformation.personalPrescriptionCode);
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
            RegisterProxy.savePrivatlakare(RegisterModel.get());
        };
    });
