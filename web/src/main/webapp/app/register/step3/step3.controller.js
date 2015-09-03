angular.module('privatlakareApp')
    .controller('Step3Ctrl',
    function($scope, $log, $state, $window, RegisterViewState, UserModel, RegisterModel, RegisterProxy,
        WindowUnload) {
        'use strict';

        RegisterViewState.updateStep();

        var user = UserModel.init();
        if (UserModel.isRegistered()) {
            $state.go('app.register.complete');
        }

        var model = RegisterModel.init();
        var privatLakareDetails = RegisterViewState.getRegisterDetailsTableDataFromModel(user, model);
        $scope.uppgifter = privatLakareDetails.uppgifter;
        $scope.kontaktUppgifter = privatLakareDetails.kontaktUppgifter;
        $scope.viewState = RegisterViewState;
        $scope.registerModel = model;

        // Skapa konto button
        $scope.createAccount = function() {
            RegisterViewState.loading.register = true;
            RegisterProxy.registerPrivatlakare(model).then(function(successData) {
                $log.debug('Registration complete - data:');
                $log.debug(successData);
                RegisterViewState.loading.register = false;
                RegisterViewState.errorMessage.register = null;
                user.status = successData.status;
                RegisterModel.reset();

                switch (user.status) {
                case 'AUTHORIZED':
                    $state.go('app.register.complete');
                    break;
                case 'NOT_AUTHORIZED':
                case 'WAITING_FOR_HOSP':
                    $state.go('app.register.waiting');
                    break;
                default: // NOT_STARTED, UNKNOWN or other unwanted values like null or undefined
                    RegisterViewState.errorMessage.register =
                        'Kunde inte registrera privatläkare på grund av tekniskt fel. Försök igen senare.';
                    $log.debug('Invalid user status in response:' + user.status);
                }

            }, function(errorData) {
                RegisterViewState.loading.register = false;
                RegisterViewState.errorMessage.register =
                    'Kunde inte registrera privatläkare på grund av tekniskt fel. Försök igen senare.';
                $log.debug('Failed to register errorCode:' + errorData.errorCode + ' reason:' + errorData.message);
            });
        };

        // Add browser dialog to ask if user wants to save before leaving if he closes the window on an edited form.
        WindowUnload.bindUnload($scope, RegisterViewState.windowUnloadWarningCondition);
    });
