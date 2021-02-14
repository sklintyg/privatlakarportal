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

describe('Model: RegisterModel', function() {
  'use strict';

  // Load the module and mock away everything that is not necessary.
  beforeEach(angular.mock.module('privatlakareApp', function($provide) {
    $provide.value('APP_CONFIG', {});
  }));

  var RegisterModel, $rootScope, $httpBackend;
  /*
      var testPostDTO = {
          'registration': {
              'befattning':'201013',
              'verksamhetensNamn':'Kliniken','agarForm':'Privat',
              'vardform':'03',
              'verksamhetstyp':'12',
              'arbetsplatskod':'0123456',
              'telefonnummer':'070123456','epost':'a@a.a',
              'adress':'Gatan','postnummer':'12345','postort':'Linköping','kommun':'Linköping','lan':'Östergötland'
          }
      };
  */
  var testGetDTO = {
    'registration': {
      'befattning': '201013',
      'verksamhetensNamn': 'Kliniken', 'agarForm': 'Privat',
      'vardform': '03',
      'verksamhetstyp': '12',
      'arbetsplatskod': '0123456',
      'telefonnummer': '070123456', 'epost': 'a@a.a',
      'adress': 'Gatan', 'postnummer': '12345', 'postort': 'Linköping', 'kommun': 'Linköping', 'lan': 'Östergötland',
      'legitimeradYrkesgrupp': 'Läkare', 'specialitet': 'Allmän medicin', 'forskrivarkod': '0123456'
    }
  };

  var testConvertedViewModel = {
    'registration': {
      'befattning': {'id': '201013'},
      'verksamhetensNamn': 'Kliniken', 'agarForm': 'Privat',
      'vardform': {'id': '03'},
      'verksamhetstyp': {'id': '12'},
      'arbetsplatskod': '0123456',
      'telefonnummer': '070123456', 'epost': 'a@a.a', 'epost2': 'a@a.a',
      'adress': 'Gatan', 'postnummer': '12345', 'postort': 'Linköping', 'kommun': 'Linköping', 'lan': 'Östergötland',
      'legitimeradYrkesgrupp': 'Läkare', 'specialitet': 'Allmän medicin', 'forskrivarkod': '0123456'
    }
  };

  var testViewModel = {
    'befattning': {'id': '201013', 'label': 'Företagsläkare'},
    'verksamhetensNamn': 'Kliniken', 'agarForm': 'Privat',
    'vardform': {'id': '03', 'label': 'Hemsjukvård'},
    'verksamhetstyp': {'id': '12', 'label': 'Laboratorieverksamhet'},
    'arbetsplatskod': '0123456',
    'telefonnummer': '070123456', 'epost': 'a@a.a', 'epost2': 'a@a.a',
    'adress': 'Gatan', 'postnummer': '12345', 'postort': 'Linköping', 'kommun': 'Linköping', 'lan': 'Östergötland',
    'legitimeradYrkesgrupp': 'Läkare', 'specialitet': 'Allmän medicin', 'forskrivarkod': '0123456'
  };

  // Initialize the controller and a mock scope
  beforeEach(inject(function(_$rootScope_, _$httpBackend_, _RegisterModel_) {
    $rootScope = _$rootScope_;
    $httpBackend = _$httpBackend_;
    RegisterModel = _RegisterModel_;
  }));

  describe('convertToViewModel', function() {
    it('should convert dto structures to viewModels', function() {
      var viewModel = RegisterModel.convertToViewModel(testGetDTO);
      expect(viewModel).toEqual(testConvertedViewModel);
    });
  });

  describe('convertToDTO', function() {
    it('should convert viewModels to dto structures', function() {
      var dto = RegisterModel.convertToDTO(testViewModel);
      expect(dto).toEqual(testGetDTO);
    });
  });

});
