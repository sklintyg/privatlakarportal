angular.module('privatlakareApp')
  .controller('Step3Ctrl', function ($scope, $log, $state, $window,
        RegisterViewStateService, UserModel, RegisterModel, RegisterProxy, WindowUnload) {
        'use strict';

        RegisterViewStateService.updateStep();

        var user = UserModel.init();
        if(UserModel.isRegistered()) {
            $state.go('app.register.complete');
        }

        var model = RegisterModel.init();
        var privatLakareDetails = RegisterViewStateService.getRegisterDetailsTableDataFromModel(user, model);
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
                UserModel.get().status = successData.status;
                RegisterModel.reset();
            }, function(errorData) {
                RegisterViewStateService.loading.register = false;
                RegisterViewStateService.errorMessage.register = 'Kunde inte registrera privatläkare på grund av tekniskt fel. Försök igen senare.';
                $log.debug('Failed to register errorCode:' + errorData.errorCode + ' reason:' + errorData.message);
            });
        };

        // Add browser dialog to ask if user wants to save before leaving if he closes the window on an edited form.
        WindowUnload.bindUnload($scope);
  });
