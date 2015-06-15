'use strict';

describe('Controller: Step3Ctrl', function () {

  // load the controller's module
  beforeEach(module('privatlakareApp'));

  var Step3Ctrl, scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    Step3Ctrl = $controller('Step3Ctrl', {
      $scope: scope
    });
  }));

  it('should ...', function () {
    expect(1).toEqual(1);
  });
});
