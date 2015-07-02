'use strict';

describe('Controller: Step2ContainerCtrl', function () {

  // load the controller's module
  beforeEach(module('privatlakareApp'));

  var Step2ContainerCtrl, scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
      Step2ContainerCtrl = $controller('Step2ContainerCtrl', {
      $scope: scope
    });
  }));

  it('should ...', function () {
    expect(1).toEqual(1);
  });
});
