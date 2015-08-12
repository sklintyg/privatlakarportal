angular.module('privatlakareApp').service('RegisterViewStateService',
    function($state,
        HospProxy) {
        'use strict';

        this.reset = function() {
            this.step = 1;

            this.errorMessage = {
                register: null,
                pasteEpost: false,
                pasteEpost2: false
            };

            this.loading = {
                hosp: false
            };

            this.befattningList = [
                { id: '201011', label: 'Distriktsläkare/Specialist allmänmedicin' },
                { id: '201012', label: 'Skolläkare' },
                { id: '201013', label: 'Företagsläkare' },
                { id: '202010', label: 'Specialistläkare' },
                { id: '203010', label: 'Läkare legitimerad, specialiseringstjänstgöring' },
                { id: '203090', label: 'Läkare legitimerad, annan' }
            ];
            this.vardformList = [
                { id: '01', label: 'Öppenvård (förvald)' },
                { id: '02', label: 'Slutenvård' },
                { id: '03', label: 'Hemsjukvård' }
            ];
            this.verksamhetstypList = [
                { id: '10', label: 'Barn- och ungdomsverksamhet' },
                { id: '11', label: 'Medicinsk verksamhet' },
                { id: '12', label: 'Laboratorieverksamhet' },
                { id: '13', label: 'Opererande verksamhet' },
                { id: '14', label: 'Övrig medicinsk verksamhet' },
                { id: '15', label: 'Primärvårdsverksamhet' },
                { id: '16', label: 'Psykiatrisk verksamhet' },
                { id: '17', label: 'Radiologisk verksamhet' },
                { id: '18', label: 'Tandvårdsverksamhet' },
                { id: '20', label: 'Övrig medicinsk serviceverksamhet' }
            ];
        };

        this.getStepFromState = function(state) {
            return state.data.step;
        };

        this.updateStep = function() {
            this.step = this.getStepFromState($state.current);
        };

        this.navigationAllowed = function(toState, formValid) {
            // Prevent user from navigating forwards if clicking the fishbone nav
            var toStep = this.getStepFromState(toState);
            if (toStep > this.step + 1 || (toStep === this.step + 1 && !formValid)) {
                return false;
            }
            return true;
        };

        function returnJoinedArrayOrNull(value) {
            return value !== null && value !== undefined ? value.join(', ') : null;
        }

        function valueOrNull(value) {
            return value !== null && value !== undefined ? value : null;
        }

        this.decorateModelWithHospInfo = function(model) {

            this.loading.hosp = true;
            var viewState = this;

            function processHospResult(hospInfo) {
                viewState.loading.hosp = false;

                if(hospInfo === null) {
                    viewState.errorMessage.hosp = 'Kunde inte hämta information från socialstyrelsen.';
                } else {
                    viewState.errorMessage.hosp = null;
                }

                model.legitimeradYrkesgrupp = returnJoinedArrayOrNull(hospInfo.hsaTitles);
                model.specialitet = returnJoinedArrayOrNull(hospInfo.specialityNames);
                model.forskrivarkod = valueOrNull(hospInfo.personalPrescriptionCode);

                /// TEMP FIX UNTIL kommun/län lookup is implemented
                model.postort = 'Linköping';
                model.kommun = 'Linköping';
                model.lan = 'Östergötland';
            }

            HospProxy.getHospInformation().then(processHospResult, processHospResult);
        };

        this.getRegisterDetailsTableDataFromModel = function(UserModel, RegisterModel) {
            var model = RegisterModel;
            var details = {};
            details.uppgifter = [
                { id: 'personnummer', name: 'Personnummer', value: UserModel.personnummer, locked: true },
                { id: 'namn', name: 'Namn', value: UserModel.name, locked: true },
                { id: 'befattning', name: 'Befattning', value: (!model.befattning) ? '' : model.befattning.label },
                { id: 'verksamhetensnamn', name: 'Verksamhetens namn', value: model.verksamhetensNamn },
                { id: 'agarform', name: 'Ägarform', value: model.agarForm, locked: true },
                { id: 'vardform', name: 'Vårdform', value: model.vardform.label },
                { id: 'verksamhetstyp', name: 'Verksamhetstyp', value: (!model.verksamhetstyp) ? '' : model.verksamhetstyp.label },
                { id: 'arbetsplatskod', name: 'Arbetsplatskod', value: model.arbetsplatskod }
            ];

            details.kontaktUppgifter = [
                { id: 'telefonnummer', name: 'Telefonnummer', value: model.telefonnummer },
                { id: 'epost', name: 'E-post till verksamheten', value: model.epost },
                { id: 'adress', name: 'Adress till verksamheten', value: model.adress },
                { id: 'postnummer', name: 'Postnummer till verksamheten', value: model.postnummer },
                { id: 'postort', name: 'Postort till verksamheten', value: model.postort },
                { id: 'kommun', name: 'Kommun', value: model.kommun },
                { id: 'lan', name: 'Län', value: model.lan }
            ];

            return details;
        };

        this.reset();
    }
);
