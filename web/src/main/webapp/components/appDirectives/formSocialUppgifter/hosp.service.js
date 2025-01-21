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

angular.module('privatlakareApp').service('HospService',
    function($state,
        HospProxy, ObjectHelper) {
      'use strict';

      this.loadHosp = function(hospViewState, HospModel) {

        var service = this;

        function processHospResult(hospInfo) {
          service.updateHosp('load', hospViewState, HospModel, hospInfo);
        }

        hospViewState.loading.hosp = true;
        HospProxy.getHospInformation().then(processHospResult, processHospResult);
      };

      this.updateHosp = function(source, hospViewState, HospModel, hospInfo) {
        hospViewState.loading.hosp = false;
        if (!ObjectHelper.isDefined(hospInfo) ||
            (!ObjectHelper.isDefined(hospInfo.personalPrescriptionCode) &&
                !ObjectHelper.isDefined(hospInfo.hsaTitles) &&
                !ObjectHelper.isDefined(hospInfo.specialityNames))) {
          hospViewState.errorMessage.hosp = 'hosp.error.' + source;
          HospModel.reset();
        } else {
          hospViewState.errorMessage.hosp = false;
          var hospModel = HospModel.get();
          hospModel.legitimeradYrkesgrupp = ObjectHelper.returnJoinedArrayOrNull(hospInfo.hsaTitles);
          hospModel.specialitet = ObjectHelper.returnJoinedArrayOrNull(hospInfo.specialityNames);
          hospModel.forskrivarkod = ObjectHelper.valueOrNull(hospInfo.personalPrescriptionCode);
        }
        updateViewState(hospViewState, HospModel.get());
      };

      function updateViewState(hospViewState, hospModel) {
        hospViewState.socialstyrelsenUppgifter = [
          {id: 'legitimeradYrkesgrupp', name: 'Legimiterad yrkesgrupp', value: hospModel.legitimeradYrkesgrupp, locked: true},
          {id: 'specialitet', name: 'Specialitet', value: hospModel.specialitet, locked: true},
          {id: 'forskrivarkod', name: 'Förskrivarkod', value: hospModel.forskrivarkod, locked: true}
        ];
      }
    }
);
