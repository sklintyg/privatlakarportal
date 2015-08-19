angular.module('privatlakareApp')
  .controller('Step3Ctrl', function ($scope, $log, $state,
        RegisterViewStateService, UserModel, RegisterModel, RegisterProxy) {
        'use strict';

        RegisterViewStateService.updateStep();

        var model = RegisterModel.init();
        var privatLakareDetails = RegisterViewStateService.getRegisterDetailsTableDataFromModel(UserModel, model);
        $scope.uppgifter = privatLakareDetails.uppgifter;
        $scope.kontaktUppgifter = privatLakareDetails.kontaktUppgifter;
        $scope.viewState = RegisterViewStateService;
        RegisterViewStateService.decorateModelWithHospInfo();
        $scope.registerModel = model;

        // Skapa konto button
        $scope.createAccount = function(){
            RegisterViewStateService.loading.register = true;
            RegisterProxy.registerPrivatlakare(model).then(function(successData) {
                $log.debug('Registration complete - data:');
                $log.debug(successData);
                $state.go('app.register.complete');
                RegisterViewStateService.loading.register = false;
                RegisterViewStateService.errorMessage.register = null;
                RegisterModel.reset();
            }, function(errorData) {
                RegisterViewStateService.loading.register = false;
                RegisterViewStateService.errorMessage.register = 'Kunde inte registrera privatläkare på grund av tekniskt fel. Försök igen senare.';
                $log.debug('Failed to register errorCode:' + errorData.errorCode + ' reason:' + errorData.message);
            });
        };

  });
