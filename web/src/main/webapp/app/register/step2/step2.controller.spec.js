'use strict';

describe('Controller: Step2Ctrl', function () {

  // load the controller's module
  beforeEach(module('privatlakareApp'));

  var Step2Ctrl, scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    Step2Ctrl = $controller('Step2Ctrl', {
      $scope: scope
    });
  }));

  it('should ...', function () {
    expect(1).toEqual(1);
  });
});
