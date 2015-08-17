angular.module('privatlakareApp').service('RegisterViewStateService',
    function($state,
        HospProxy, ObjectHelper) {
        'use strict';

        this.reset = function() {
            this.step = 1;

            this.errorMessage = {
                register: null, // step 3
                pasteEpost: false, // step 2
                pasteEpost2: false // step 2
            };

            this.loading = {
                hosp: false, // step 3
                region: false // step 2
            };

            // move to step 2 viewstateservice
            this.validPostnummer = false;
            this.kommunOptions = null;
            this.kommunSelectionMode = false;
            this.kommunSelected = false;

            // Hosp info, move to social model, service for use in formSocialUppgifter directive
            this.legitimeradYrkesgrupp = null;
            this.specialitet = null;
            this.forskrivarkod = null;

            // move to step 1 viewstate
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

            return this;
        };

        // general step navigation
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

        // step 3 viewstate or even hosp service
        this.decorateModelWithHospInfo = function() {

            this.loading.hosp = true;
            var viewState = this;

            function processHospResult(hospInfo) {
                viewState.loading.hosp = false;
                if(!ObjectHelper.isDefined(hospInfo)) {
                    viewState.errorMessage.hosp = 'Kunde inte hämta information från socialstyrelsen.';
                    viewState.legitimeradYrkesgrupp = null;
                    viewState.specialitet = null;
                    viewState.forskrivarkod = null;
                } else {
                    viewState.errorMessage.hosp = null;
                    viewState.legitimeradYrkesgrupp = ObjectHelper.returnJoinedArrayOrNull(hospInfo.hsaTitles);
                    viewState.specialitet = ObjectHelper.returnJoinedArrayOrNull(hospInfo.specialityNames);
                    viewState.forskrivarkod = ObjectHelper.valueOrNull(hospInfo.personalPrescriptionCode);
                }
            }

            HospProxy.getHospInformation().then(processHospResult, processHospResult);
        };

        // step 3 viewstate
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
                { id: 'arbetsplatskod', name: 'Arbetsplatskod (frivilligt)', value: model.arbetsplatskod || 'Ej angivet' }
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
