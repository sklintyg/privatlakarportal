/*
 * Copyright (C) 2020 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

describe('Controller: BootCtrl', function() {
  'use strict';

  var succeed = true;
  var user = {};
  var error = {};

  // load the controller's module
  beforeEach(angular.mock.module('privatlakareApp', function($provide) {
    $provide.value('UserProxy', {
      getUser: function() {
        return {
          then: function(onSuccess, onError) {
            if (succeed) {
              onSuccess(user);
            } else {
              onError(error);
            }
          }
        };
      }
    });
  }));

  var scope, $controller, $state;

  // Initialize the controller and a mock scope
  beforeEach(inject(function(_$controller_, $rootScope, _$state_) {
    $controller = _$controller_;
    scope = $rootScope.$new();
    $state = _$state_;
  }));

  it('should redirect to step 1 if targetId parameter is "new"', function() {
    succeed = true;
    user = {name: 'Nisse', status: 'NOT_STARTED'};
    $state.params.targetId = 'new';
    spyOn($state, 'go').and.stub();
    $controller('BootLinkCtrl', {$scope: scope});
    expect($state.go).toHaveBeenCalledWith('app.register.step1');
  });

  it('should show error if invalid targetId is supplied', function() {
    succeed = true;
    user = {name: 'Nisse', status: 'NOT_STARTED'};
    $state.params.targetId = '';
    spyOn($state, 'go').and.stub();
    $controller('BootLinkCtrl', {$scope: scope});
    expect(scope.errorMessage).not.toBe(null);
  });

  it('should show error if a user is not received', function() {
    succeed = false;
    error = {};
    $state.params.targetId = '';
    $controller('BootLinkCtrl', {$scope: scope});
    expect(scope.errorMessage).not.toBe(null);
  });

});
