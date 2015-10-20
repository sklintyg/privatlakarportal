angular.module('privatlakareApp')
    .controller('MinsidaCtrl', function($scope, $state, $log, $window,
        HospModel, HospService, HospViewState, MinsidaViewState, ObjectHelper, RegisterModel, RegisterProxy,
        UserModel, WindowUnload) {

        'use strict';
        $scope.user = UserModel.get();
        $scope.registerModel = RegisterModel.reset();
        $scope.viewState = MinsidaViewState.reset();

        function updateState(lakarData) {
            if (ObjectHelper.isDefined(lakarData)) {
                RegisterModel.set(lakarData.registration);
                $scope.registerModel = RegisterModel.get();
                HospModel.init();
                HospService.updateHosp('update', HospViewState, HospModel, lakarData.hospInformation);
            } else {
                $scope.registerModel = RegisterModel.reset();
                $scope.viewState = MinsidaViewState.reset();
            }
        }

        $scope.$watch('user', function(/*newVal*/) {
            MinsidaViewState.errorMessage.noPermission = !UserModel.hasApplicationPermission();
        }, true);

        RegisterProxy.getPrivatlakare().then(function(lakarData) {
            $log.debug('MinsidaCtrl - Got privatlakaredata:');
            $log.debug(lakarData);
            updateState(lakarData);
        }, function(errorData) {
            $log.debug('MinsidaCtrl - Got error:');
            $log.debug(errorData);
            MinsidaViewState.errorMessage.load = 'Kunde inte ladda formuläret. Prova ladda om sidan. (' + errorData.message + ')';
        });

        $scope.save = function() {
            MinsidaViewState.loading.save = true;
            RegisterProxy.savePrivatlakare(RegisterModel.get()).then(function(/*successData*/) {
                MinsidaViewState.loading.save = false;
                MinsidaViewState.errorMessage.save = null;
                $scope.registerForm.$setPristine();
            }, function(errorData) {
                MinsidaViewState.loading.save = false;
                MinsidaViewState.errorMessage.save = 'Kunde inte spara ändringarna. Försök igen senare. (' + errorData.message + ')';
            });
        };

        // Add browser dialog to ask if user wants to save before leaving if he closes the window on an edited form.
        $scope.$watch('registerForm.$dirty',  function(newVal) {
            if (newVal) {
                WindowUnload.enable();
            }
            else {
                WindowUnload.disable();
            }
        });
        WindowUnload.bindUnload($scope);
    });
