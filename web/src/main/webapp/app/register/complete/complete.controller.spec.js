describe('Controller: CompleteCtrl', function () {
  'use strict';

  // load the controller's module
  beforeEach(module('privatlakareApp', function($provide) {
      $provide.value('APP_CONFIG', {});
  }));

  var CompleteCtrl, scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CompleteCtrl = $controller('CompleteCtrl', {
      $scope: scope
    });
  }));

  it('should ...', function () {
    expect(1).toEqual(1);
  });
});
