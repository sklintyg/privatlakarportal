describe('Controller: ErrorCtrl', function() {
    'use strict';

    // load the controller's module
    beforeEach(module('privatlakareApp'));

    var ErrorCtrl, scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function($controller, $rootScope) {
        scope = $rootScope.$new();
        ErrorCtrl = $controller('ErrorCtrl', {
            $scope: scope
        });
    }));

    it('should ...', function() {
        expect(1).toEqual(1);
    });
});
