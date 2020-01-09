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

describe('Router: Abort', function() {
  'use strict';

  var rootScope;
  var state;
  var dialogService;

  // load the controller's module
  beforeEach(module('privatlakareApp', function($provide) {
    $provide.value('APP_CONFIG', {});
  }));

  beforeEach(angular.mock.module('htmlTemplates'));

  beforeEach(inject(function(_$rootScope_, _$state_, _dialogService_) {
    rootScope = _$rootScope_;
    state = _$state_;
    dialogService = _dialogService_;
    spyOn(dialogService, 'open').and.callThrough();
    spyOn(dialogService, 'close').and.callThrough();
  }));

  it('should open dialog when entering state "app.register.step1.abort" and close it when leaving the state', function() {
    state.go('app.register.step1');
    rootScope.$apply();

    state.go('app.register.step1.abort');
    rootScope.$apply();
    expect(dialogService.open).toHaveBeenCalled();

    state.go('app.register.step1');
    rootScope.$apply();
    expect(dialogService.close).toHaveBeenCalled();
  });

});