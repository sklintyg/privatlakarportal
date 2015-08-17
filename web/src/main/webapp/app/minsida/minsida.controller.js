angular.module('privatlakareApp')
    .controller('MinsidaCtrl', function($scope, $state, $log,
        UserModel, RegisterModel, RegisterViewStateService, RegisterProxy, ObjectHelper) {
        'use strict';
        $scope.user = UserModel;
        $scope.registerModel = RegisterModel.reset();
        $scope.viewState = RegisterViewStateService.reset();

        RegisterProxy.getPrivatlakare().then(function(lakarData) {
            $log.debug('MinsidaCtrl - Got privatlakaredata:');
            $log.debug(lakarData);

            if(ObjectHelper.isDefined(lakarData)) {
                $log.debug('sagae');
                RegisterModel.set(lakarData.registration);
                $scope.registerModel = RegisterModel.get();
                $scope.registerModel.legitimeradYrkesgrupp = ObjectHelper.returnJoinedArrayOrNull(lakarData.hospInformation.hsaTitles);
                $scope.registerModel.specialitet = ObjectHelper.returnJoinedArrayOrNull(lakarData.hospInformation.specialityNames);
                $scope.registerModel.forskrivarkod = ObjectHelper.valueOrNull(lakarData.hospInformation.personalPrescriptionCode);
            } else {
                $scope.registerModel = RegisterModel.reset();
                $scope.viewState = RegisterViewStateService.reset();
            }
        }, function(errorData) {
            $log.debug('MinsidaCtrl - Got error:');
            $log.debug(errorData);
        });


        $scope.save = function() {
            RegisterProxy.savePrivatlakare(RegisterModel.get());
        };
    });
