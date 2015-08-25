'use strict';

describe('Controller: BootCtrl', function () {

  // load the controller's module
  beforeEach(module('privatlakareApp'));

  var BootCtrl, scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    BootCtrl = $controller('BootCtrl', {
      $scope: scope
    });
  }));

  it('should ...', function () {
    expect(1).toEqual(1);
  });
});
