angular.module('privatlakareApp').service('HospService',
    function($state,
        HospProxy, ObjectHelper) {
        'use strict';

        this.bindScope = function(scope, hospViewState, hospModel) {
            scope.model = hospModel;
            scope.$watch('model', function(newVal) {
                hospViewState.socialstyrelsenUppgifter = [
                    { id: 'legitimeradYrkesgrupp', name: 'Legimiterad yrkesgrupp', value: newVal.legitimeradYrkesgrupp, locked: true },
                    { id: 'specialitet', name: 'Specialitet', value: newVal.specialitet, locked: true },
                    { id: 'forskrivarkod', name: 'Förskrivarkod', value: newVal.forskrivarkod, locked: true }
                ];
            }, true);
        };

        this.loadHosp = function(hospViewState, hospModel) {

            function processHospResult(hospInfo) {
                hospViewState.loading.hosp = false;
                if(!ObjectHelper.isDefined(hospInfo)) {
                    hospViewState.errorMessage.hosp = 'Kunde inte hämta information från socialstyrelsen.';
                    hospModel.legitimeradYrkesgrupp = null;
                    hospModel.specialitet = null;
                    hospModel.forskrivarkod = null;
                } else {
                    hospViewState.errorMessage.hosp = null;
                    hospModel.legitimeradYrkesgrupp = ObjectHelper.returnJoinedArrayOrNull(hospInfo.hsaTitles);
                    hospModel.specialitet = ObjectHelper.returnJoinedArrayOrNull(hospInfo.specialityNames);
                    hospModel.forskrivarkod = ObjectHelper.valueOrNull(hospInfo.personalPrescriptionCode);
                }
            }

            hospViewState.loading.hosp = true;
            HospProxy.getHospInformation().then(processHospResult, processHospResult);
        };
    }
);
