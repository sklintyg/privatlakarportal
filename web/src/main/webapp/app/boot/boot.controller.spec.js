describe('Controller: BootCtrl', function() {
    'use strict';

    var succeed = true;
    var user = {};
    var error = {};

    // load the controller's module
    beforeEach(angular.mock.module('htmlTemplates'));
    beforeEach(angular.mock.module('privatlakareApp', function($provide) {
        $provide.value('UserProxy', {
            getUser: function() {
                return {
                    then: function(onSuccess, onError) {
                        if (succeed) {
                            onSuccess(user);
                        } else {
                            onError(error);
                        }
                    }
                };
            }
        });
    }));

    var $rootScope , $controller, $state, BootCtrl, scope, UserModel, mockResponse;

    // Initialize the controller and a mock scope
    beforeEach(inject(function(_$controller_, _$rootScope_, _UserModel_, _$state_, _mockResponse_) {
        $rootScope = _$rootScope_;
        $controller = _$controller_;
        scope = $rootScope.$new();
        UserModel = _UserModel_;
        $state = _$state_;
        mockResponse = _mockResponse_;
    }));

    it('should be redirected to start page if not registered yet', function() {
        spyOn($state, 'go').and.stub();
        succeed = true;

        BootCtrl = $controller('BootCtrl', { $scope: scope });
        user = angular.copy(mockResponse.userModel);
        user.status = 'NOT_STARTED';
        user.nameFromPuService = true;
        UserModel.set(user);
        $rootScope.$digest();

        expect($state.go).toHaveBeenCalledWith('app.start');
    });

    it('should be redirected to error page if not registered yet and unable to get name from pu serivce', function() {
        spyOn($state, 'go').and.stub();
        succeed = true;

        BootCtrl = $controller('BootCtrl', { $scope: scope });
        user = angular.copy(mockResponse.userModel);
        user.status = 'NOT_STARTED';
        user.nameFromPuService = false;
        UserModel.set(user);
        $rootScope.$digest();

        expect($state.go).toHaveBeenCalledWith('app.error',
            { errorMessage: 'Ett tekniskt fel har tyvärr uppstått och det går inte att hämta dina namnuppgifter ' +
                'från folkbokföringsregistret för tillfället. Du kan därför inte skapa ett konto för Webcert just nu. ' +
                'Prova igen om en stund.' });
    });

    it('should be redirected to minsida page if registered but havent received läkarlegimitation yet', function() {
        spyOn($state, 'go').and.stub();
        succeed = true;

        user = angular.copy(mockResponse.userModel);
        user.status = 'WAITING_FOR_HOSP';
        UserModel.set(user);
        BootCtrl = $controller('BootCtrl', { $scope: scope });
        $rootScope.$digest();

        expect($state.go).toHaveBeenCalledWith('app.minsida');
    });

    it('should be redirected to minsida page if registered and got läkarlegitimation', function() {
        spyOn($state, 'go').and.stub();
        succeed = true;

        BootCtrl = $controller('BootCtrl', { $scope: scope });
        user = angular.copy(mockResponse.userModel);
        user.status = 'AUTHORIZED';
        UserModel.set(user);
        $rootScope.$digest();

        expect($state.go).toHaveBeenCalledWith('app.minsida');
    });

});
