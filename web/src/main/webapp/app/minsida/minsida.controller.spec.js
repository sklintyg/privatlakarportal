describe('Controller: MinsidaCtrl', function() {
    'use strict';

    // load the controller's module
    beforeEach(module('privatlakareApp', function($provide) {
        $provide.value('APP_CONFIG', {});
    }));

    var MinsidaCtrl, scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function($controller, $rootScope, $window) {
        scope = $rootScope.$new();
        scope.registerForm = {
            $dirty: true
        };
        MinsidaCtrl = $controller('MinsidaCtrl', {
            $scope: scope
        });
        $window.onbeforeunload = function() {};
    }));

    it('should ...', function() {
        expect(1).toEqual(1);
    });
});
