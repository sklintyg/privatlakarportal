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

describe('Service: RegisterNavigation', function() {
  'use strict';

  // Load the module and mock away everything that is not necessary.
  beforeEach(angular.mock.module('privatlakareApp', function() {
  }));

  var state;
  var registerNavigationService;

  beforeEach(inject(function(_$state_, _RegisterNavigationService_) {
    state = _$state_;
    registerNavigationService = _RegisterNavigationService_;
  }));

  it('should return nul1 from non step state', function() {
    var step = registerNavigationService.getStepFromState(state.get('app.minsida'));
    expect(step).toBe(null);
  });

  it('should return step number 1 from state "app.register.step1"', function() {
    var step = registerNavigationService.getStepFromState(state.get('app.register.step1'));
    expect(step).toBe(1);
  });

  it('should allow navitaion from step1 to any non step"', function() {
    var navigationAllowed = registerNavigationService.navigationAllowed(state.get('app.minsida'), state.get('app.register.step2'), false);
    expect(navigationAllowed).toBe(true);
  });

  it('should not allow navitaion from step1 to step2 if form is not valid"', function() {
    var navigationAllowed = registerNavigationService.navigationAllowed(state.get('app.register.step2'), state.get('app.register.step1'),
        false);
    expect(navigationAllowed).toBe(false);
  });

  it('should allow navitaion from step1 to step2 if form is valid"', function() {
    var navigationAllowed = registerNavigationService.navigationAllowed(state.get('app.register.step2'), state.get('app.register.step1'),
        true);
    expect(navigationAllowed).toBe(true);
  });

  it('should not allow navitaion from step1 to step3"', function() {
    var navigationAllowed = registerNavigationService.navigationAllowed(state.get('app.register.step3'), state.get('app.register.step1'),
        true);
    expect(navigationAllowed).toBe(false);
  });

});
