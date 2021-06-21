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

describe('Proxy: SubscriptionProxy', function() {
  'use strict';

  beforeEach(angular.mock.module('htmlTemplates'));
  beforeEach(angular.mock.module('privatlakareApp', function() {

  }));

  var SubscriptionProxy, $rootScope, $httpBackend;

  beforeEach(inject(function(_$rootScope_, _$httpBackend_, _SubscriptionProxy_) {
    $httpBackend = _$httpBackend_;
    SubscriptionProxy = _SubscriptionProxy_;
    $rootScope = _$rootScope_;
  }));

  describe('SubscriptionProxy', function() {
    it('should get subscription information', function() {

      var onSuccess = jasmine.createSpy('onSuccess');
      var onError = jasmine.createSpy('onError');

      $httpBackend.expectGET('/api/subscription').respond(200, {subscription: {subscriptionInUse: true}});

      SubscriptionProxy.getSubscription().then(onSuccess, onError);
      $httpBackend.flush();
      $rootScope.$apply();

      expect(onSuccess).toHaveBeenCalledWith({subscription: {subscriptionInUse: true}});
      expect(onError).not.toHaveBeenCalled();
    });

    it('should fail when fetching subscription information', function() {

      var onSuccess = jasmine.createSpy('onSuccess');
      var onError = jasmine.createSpy('onError');

      $httpBackend.expectGET('/api/subscription').respond(400, {});

      SubscriptionProxy.getSubscription().then(onSuccess, onError);
      $httpBackend.flush();
      $rootScope.$apply();

      expect(onSuccess).not.toHaveBeenCalled();
      expect(onError).toHaveBeenCalled();
    });
  });
});
