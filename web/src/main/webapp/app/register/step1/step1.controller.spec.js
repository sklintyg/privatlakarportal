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

describe('Controller: Step1Ctrl', function() {
  'use strict';

  var state;

  // load the controller's module
  beforeEach(module('htmlTemplates'));
  beforeEach(module('privatlakareApp', function($provide) {
    $provide.value('APP_CONFIG', {});
  }));

  var scope;
  var UserModel;
  var RegisterNavigationService;

  describe('when user is registered', function() {
    beforeEach(inject(function($controller, $rootScope, _$state_, _UserModel_) {
      scope = $rootScope.$new();
      scope.registerForm = {};
      state = _$state_;
      UserModel = _UserModel_;

      spyOn(UserModel, 'isRegistered').and.callFake(function() {
        return true;
      });

      $controller('Step1Ctrl', {
        $scope: scope
      });
    }));

    it('should go to state app.register.complete if user is registered', function() {
      scope.$apply();
      expect(state.is('app.register.complete')).toBe(true);
    });
  });

  describe('when user is not registered', function() {
    beforeEach(inject(function($controller, $rootScope, _$state_, _UserModel_, _RegisterNavigationService_) {
      scope = $rootScope.$new();
      scope.registerForm = {
        $valid: true
      };
      state = _$state_;
      UserModel = _UserModel_;
      RegisterNavigationService = _RegisterNavigationService_;

      spyOn(UserModel, 'isRegistered').and.callFake(function() {
        return false;
      });

      $controller('Step1Ctrl', {
        $scope: scope
      });
    }));

    it('should stay on state app.register.step1 if user is registered', function() {
      scope.$apply();
      expect(state.is('app.register.complete')).toBe(false);
    });

    it('should go to step2 when form submitted and valid', function() {
      spyOn(RegisterNavigationService, 'navigationAllowed').and.callFake(function() {
        return true;
      });
      scope.submitForm();
      scope.$apply();
      expect(state.is('app.register.step2')).toBe(true);
    });

    it('should not go to step2 when form submitted and valid', function() {
      spyOn(RegisterNavigationService, 'navigationAllowed').and.callFake(function() {
        return false;
      });
      scope.submitForm();
      scope.$apply();
      expect(state.is('app.register.step2')).toBe(false);
    });
  });
});