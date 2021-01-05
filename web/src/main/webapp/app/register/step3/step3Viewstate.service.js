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

angular.module('privatlakareApp').service('Step3ViewState',
    function() {
      'use strict';

      this.reset = function() {
        this.step = 3;

        this.errorMessage = {
          register: null
        };

        this.loading = {
          register: false
        };

        this.godkannvillkor = false;

        return this;
      };

      this.getRegisterDetailsTableDataFromModel = function(UserModel, RegisterModel) {
        var model = RegisterModel;
        var details = {};
        details.uppgifter = [
          {id: 'personnummer', name: 'Personnummer', value: UserModel.personnummer, locked: true},
          {id: 'namn', name: 'Namn', value: UserModel.name, locked: true},
          {id: 'befattning', name: 'Befattning', value: (!model.befattning) ? '' : model.befattning.label},
          {id: 'verksamhetensnamn', name: 'Verksamhetens namn', value: model.verksamhetensNamn},
          {id: 'agarform', name: 'Ägarform', value: model.agarForm, locked: true},
          {id: 'vardform', name: 'Vårdform', value: model.vardform.label},
          {id: 'verksamhetstyp', name: 'Verksamhetstyp', value: (!model.verksamhetstyp) ? '' : model.verksamhetstyp.label},
          {id: 'arbetsplatskod', name: 'Arbetsplatskod (frivilligt)', value: model.arbetsplatskod || 'Ej angivet'}
        ];

        details.kontaktUppgifter = [
          {id: 'telefonnummer', name: 'Telefonnummer', value: model.telefonnummer},
          {id: 'epost', name: 'E-post till verksamheten', value: model.epost},
          {id: 'adress', name: 'Adress till verksamheten', value: model.adress},
          {id: 'postnummer', name: 'Postnummer till verksamheten', value: model.postnummer},
          {id: 'postort', name: 'Postort till verksamheten', value: model.postort},
          {id: 'kommun', name: 'Kommun', value: model.kommun},
          {id: 'lan', name: 'Län', value: model.lan}
        ];

        return details;
      };

      this.reset();
    }
);
