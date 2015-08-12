angular.module('privatlakareApp')
  .controller('Step3Ctrl', function ($scope, $log, $state,
        RegisterViewStateService, UserModel, RegisterModel, RegisterProxy) {
        'use strict';

        RegisterViewStateService.updateStep();

        var model = RegisterModel.init();
        var privatLakareDetails = RegisterViewStateService.getRegisterDetailsTableDataFromModel(model);
        $scope.uppgifter = privatLakareDetails.uppgifter;
        $scope.kontaktUppgifter = privatLakareDetails.kontaktUppgifter;
        $scope.viewState = RegisterViewStateService;

        RegisterViewStateService.decorateModelWithHospInfo(model);

        // Skapa konto button
        $scope.createAccount = function(){
            RegisterProxy.registerPrivatlakare(model).then(function(successData) {
                $log.debug('Registration complete - data:');
                $log.debug(successData);
                $state.go('app.register.complete');
                RegisterViewStateService.registerErrorMessage = null;
                RegisterModel.reset();
            }, function(errorData) {
                RegisterViewStateService.registerErrorMessage = 'Kunde inte registrera privatläkare. (' + errorData.message + ')';
                $log.debug('Failed to register errorCode:' + errorData.errorCode + ' reason:' + errorData.message);
            });
        };

  });
