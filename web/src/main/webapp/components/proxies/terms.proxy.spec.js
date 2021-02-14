/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
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

describe('Proxy: TermsProxy', function() {
  'use strict';

  // Load the module and mock away everything that is not necessary.
  beforeEach(angular.mock.module('htmlTemplates'));
  beforeEach(angular.mock.module('privatlakareApp', function(/*$provide*/) {

  }));

  var TermsProxy, mockResponse, $rootScope, $httpBackend;

  // Initialize the controller and a mock scope

  beforeEach(inject(function(_$rootScope_, _$httpBackend_, _TermsProxy_, _mockResponse_) {
    $httpBackend = _$httpBackend_;
    TermsProxy = _TermsProxy_;
    $rootScope = _$rootScope_;
    mockResponse = _mockResponse_;
  }));

  describe('TermsProxy', function() {
    it('should get the logged in user', function() {

      var onSuccess = jasmine.createSpy('onSuccess');
      var onError = jasmine.createSpy('onError');

      $httpBackend.expectGET('/api/terms/webcert').respond(mockResponse.termsOK);

      TermsProxy.getTerms('webcert').then(onSuccess, onError);
      $httpBackend.flush();
      // promises are resolved/dispatched only on next $digest cycle
      $rootScope.$apply();

      expect(onSuccess).toHaveBeenCalledWith(mockResponse.termsModel);
      expect(onError).not.toHaveBeenCalled();
    });
  });
});
