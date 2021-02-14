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

describe('Model: UserModel', function() {
  'use strict';

  // Load the module and mock away everything that is not necessary.
  beforeEach(angular.mock.module('privatlakareApp', function(/*$provide*/) {
    //$provide.value('APP_CONFIG', {});
  }));

  var UserModel;

  // Initialize the controller and a mock scope
  beforeEach(inject(function(_UserModel_) {
    UserModel = _UserModel_;
  }));

  describe('set', function() {
    it('should add dash personnummer without', function() {
      UserModel.set({personalIdentityNumber: '191212121212'});
      expect(UserModel.get().personnummer).toEqual('19121212-1212');
    });
    it('should not add dash personnummer that already has a dash', function() {
      UserModel.set({personalIdentityNumber: '19121212-1212'});
      expect(UserModel.get().personnummer).toEqual('19121212-1212');
    });
  });

});
