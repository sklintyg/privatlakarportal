describe('Controller: Step1Ctrl', function() {
    'use strict';

    // load the controller's module
    beforeEach(module('privatlakareApp', function($provide) {
        $provide.value('$state', {current:{data:{step:1}}});
    }));

    var Step1Ctrl, scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function($controller, $rootScope) {
        scope = $rootScope.$new();
        Step1Ctrl = $controller('Step1Ctrl', {
            $scope: scope
        });
    }));

    it('should ...', function() {
        expect(1).toEqual(1);
    });
});
