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

describe('Service: LinkBuilder', function() {
  'use strict';

  // Load the module and mock away everything that is not necessary.
  beforeEach(angular.mock.module('privatlakareApp', function($provide) {
    $provide.value('APP_CONFIG', {webcertUrl: 'starturl'});
  }));

  var LinkBuilder, $rootScope, $httpBackend;

  // Initialize the controller and a mock scope
  beforeEach(inject(function(_$rootScope_, _$httpBackend_, _LinkBuilder_) {
    $rootScope = _$rootScope_;
    $httpBackend = _$httpBackend_;
    LinkBuilder = _LinkBuilder_;
  }));

  describe('getExitLink', function() {
    it('should link to minsida page if user is on complete page', function() {
      var exitLink = LinkBuilder.getExitLink('app.register.step3', 'app.register.complete', 'AUTHORIZED');
      expect(exitLink.name).toBe('Ändra uppgifter');
      expect(exitLink.link).toBe('/#/minsida');
    });
    it('should link to minsida page if user is on waiting page', function() {
      var exitLink = LinkBuilder.getExitLink('app.register.step3', 'app.register.waiting', 'WAITING_FOR_HOSP');
      expect(exitLink.name).toBe('Ändra uppgifter');
      expect(exitLink.link).toBe('/#/minsida');
    });
    it('should link to complete page if user is ready to use webcert', function() {
      var exitLink = LinkBuilder.getExitLink('app.register.complete', 'app.minsida', 'AUTHORIZED');
      expect(exitLink.name).toBe('Tillbaka');
      expect(exitLink.link).toBe('/#/registrera/klar');
    });
    it('should link to waiting page if user is not ready to use webcert', function() {
      var exitLink = LinkBuilder.getExitLink('app.register.waiting', 'app.minsida', 'WAITING_FOR_HOSP');
      expect(exitLink.name).toBe('Tillbaka');
      expect(exitLink.link).toBe('/#/registrera/vanta');

      exitLink = LinkBuilder.getExitLink('app.register.waiting', 'app.minsida', 'NOT_AUTHORIZED');
      expect(exitLink.name).toBe('Tillbaka');
      expect(exitLink.link).toBe('/#/registrera/vanta');
    });
    it('should link to webcert if user came from webcert', function() {
      var exitLink = LinkBuilder.getExitLink('', 'app.minsida', 'NOT_AUTHORIZED');
      expect(exitLink.name).toBe('Tillbaka');
      expect(exitLink.link).toBe('starturl');
    });
  });
});
