describe('Controller: MinsidaCtrl', function() {
    'use strict';

    // load the controller's module
    beforeEach(module('privatlakareApp'));

    var MinsidaCtrl, scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function($controller, $rootScope) {
        scope = $rootScope.$new();
        MinsidaCtrl = $controller('MinsidaCtrl', {
            $scope: scope
        });
    }));

    it('should ...', function() {
        expect(1).toEqual(1);
    });
});
