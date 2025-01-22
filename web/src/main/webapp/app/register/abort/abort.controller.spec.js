/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
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

describe('Controller: RegisterAbortCtrl', function() {
  'use strict';

  // load the controller's module
  beforeEach(module('privatlakareApp', function($provide) {
    $provide.value('APP_CONFIG', {});
  }));

  var scope, UserModel, RegisterModel;

  // Initialize the controller and a mock scope
  beforeEach(inject(function($controller, $rootScope, _UserModel_, _RegisterModel_) {
    scope = $rootScope.$new();
    $controller('RegisterAbortCtrl', {
      $scope: scope
    });

    UserModel = _UserModel_;
    RegisterModel = _RegisterModel_;
    RegisterModel.reset();
  }));

  it('should clear user data and logout on confirm abort', function() {
    spyOn(UserModel, 'logout').and.callFake(function() {
    });

    var model = RegisterModel.init();
    model.verksamhetensNamn = 'Kliniken';

    scope.abort();

    // RegisterModel and sessionstorage
    expect(RegisterModel.get().verksamhetensNamn).toEqual(null);
    expect(UserModel.logout).toHaveBeenCalled();
  });

  it('should not clear user data on close', function() {

    scope.$dismiss = jasmine.createSpy('$dismiss');

    var model = RegisterModel.init();
    model.verksamhetensNamn = 'Kliniken';

    scope.dismiss();

    // RegisterModel and sessionstorage
    expect(RegisterModel.get().verksamhetensNamn).toEqual('Kliniken');
    expect(scope.$dismiss).toHaveBeenCalled();
  });

});
