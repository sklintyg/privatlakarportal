describe('Controller: BootCtrl', function() {
    'use strict';

    var succeed = true;
    var user = {};
    var error = {};

    // load the controller's module
    beforeEach(angular.mock.module('privatlakareApp', function($provide) {
        $provide.value('UserProxy', {
            getUser: function() {
                return {
                    then: function(onSuccess, onError) {
                        if(succeed) {
                            onSuccess(user);
                        } else {
                            onError(error);
                        }
                    }
                };
            }
        });
    }));

    var BootCtrl, scope, $controller, UserModel, $state;

    // Initialize the controller and a mock scope
    beforeEach(inject(function(_$controller_, $rootScope, _UserModel_, _$state_) {
        $controller = _$controller_;
        scope = $rootScope.$new();
        UserModel = _UserModel_;
        $state = _$state_;
    }));

    it('should be redirected to start page if not registered yet', function() {
        succeed = true;
        user = {name:'Nisse', status: 'NOT_STARTED'};
        spyOn($state, 'go').and.stub();
        BootCtrl = $controller('BootCtrl', { $scope: scope });
        expect($state.go).toHaveBeenCalledWith('app.start');
    });

    it('should be redirected to minsida page if registered but havent received läkarlegimitation yet', function() {
        succeed = true;
        user = {name:'Nisse', status: 'WAITING_FOR_HOSP'};
        spyOn($state, 'go').and.stub();
        BootCtrl = $controller('BootCtrl', { $scope: scope });
        expect($state.go).toHaveBeenCalledWith('app.minsida');
    });

    it('should be redirected to minsida page if registered and got läkarlegitimation', function() {
        succeed = true;
        user = {name:'Nisse', status: 'COMPLETE'};
        spyOn($state, 'go').and.stub();
        BootCtrl = $controller('BootCtrl', { $scope: scope });
        expect($state.go).toHaveBeenCalledWith('app.minsida');
    });

    it('should be redirected to fake login page if not logged in or user couldnt be fetched', function() {
        succeed = false;
        error = {};
        spyOn(UserModel, 'logout').and.stub();
        BootCtrl = $controller('BootCtrl', { $scope: scope });
        expect(scope.errorMessage).not.toBe(null);
    });

});
