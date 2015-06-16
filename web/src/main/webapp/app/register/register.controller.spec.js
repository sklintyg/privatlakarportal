describe('Controller: RegisterCtrl', function() {
    'use strict';

    // load the controller's module
    beforeEach(module('privatlakareApp'));

    var RegisterCtrl, scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function($controller, $rootScope) {
        scope = $rootScope.$new();
        RegisterCtrl = $controller('RegisterCtrl', {
            $scope: scope
        });
    }));

    it('should ...', function() {
        expect(1).toEqual(1);
    });
});
