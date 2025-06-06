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

describe('Router: Terms', function() {
  'use strict';

  var rootScope;
  var state;
  var AppTermsModalModel;
  var TermsService;

  // load the controller's module
  beforeEach(module('privatlakareApp', function($provide) {
    $provide.value('APP_CONFIG', {});
  }));

  beforeEach(angular.mock.module('htmlTemplates'));

  beforeEach(inject(function(_$rootScope_, _$state_, _TermsService_, _AppTermsModalModel_) {
    rootScope = _$rootScope_;
    state = _$state_;
    TermsService = _TermsService_;
    AppTermsModalModel = _AppTermsModalModel_;
    spyOn(TermsService, 'openTerms').and.callThrough();
    var modalInstance = {
      dismiss: function() {
      }
    };
    spyOn(modalInstance, 'dismiss').and.callThrough();
    spyOn(AppTermsModalModel, 'get').and.callFake(function() {
      return {
        modalInstance: modalInstance
      };
    });
  }));

  it('should open external app terms dialog when entering state "app.start.terms" and close it when leaving the state', function() {
    state.go('app.start');
    rootScope.$apply();
    state.go('app.start.terms');
    rootScope.$apply();
    expect(TermsService.openTerms).toHaveBeenCalled();

    var modal = AppTermsModalModel.get().modalInstance;

    state.go('app.register.step1');
    rootScope.$apply();
    expect(modal.dismiss).toHaveBeenCalled();
  });

  it('should go to "app.start" state if not already on "app.start"', function() {
    state.go('app.minsida');
    rootScope.$apply();
    state.go('app.start.terms');
    rootScope.$apply();
    expect(state.is('app.start')).toBe(true);
  });

  it('should open portal terms dialog when entering state "app.register.step3.terms" and close it when leaving the state', function() {
    state.go('app.register.step3');
    rootScope.$apply();
    state.go('app.register.step3.terms');
    rootScope.$apply();
    expect(TermsService.openTerms).toHaveBeenCalled();

    var modal = AppTermsModalModel.get().modalInstance;

    state.go('app.register.step3');
    rootScope.$apply();
    expect(modal.dismiss).toHaveBeenCalled();
  });

});
