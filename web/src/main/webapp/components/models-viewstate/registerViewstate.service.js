angular.module('privatlakareApp').service('RegisterViewState',
    function($state,
        APP_CONFIG, ObjectHelper) {
        'use strict';

        this.reset = function() {
            this.step = 1;

            this.errorMessage = {
                register: null, // step 3
                pasteEpost: false, // step 2
                pasteEpost2: false, // step 2
                noPermission: false // minsida
            };

            this.loading = {
                register: false, // step 3
                region: false, // step 2
                save: false // minsida
            };

            this.windowUnloadWarningCondition = { condition: true };

            // move to step 2 viewstateservice
            this.validPostnummer = false;
            this.kommunOptions = null;
            this.kommunSelectionMode = false;
            this.kommunSelected = false;

            function processListOptions(data) {
                var list = [];
                angular.forEach(data, function(value, key) {
                    this.push({
                        'id':key,
                        'label':value
                    });
                }, list);
                return list;
            }

            // move to step 1 viewstate
            this.befattningList = processListOptions(APP_CONFIG.befattningar);
            this.vardformList = processListOptions(APP_CONFIG.vardformer);
            this.verksamhetstypList = processListOptions(APP_CONFIG.verksamhetstyper);

            // move to step 3 viewstate
            this.godkannvillkor = false;

            return this;
        };

        // general step navigation
        this.getStepFromState = function(state) {
            if(!ObjectHelper.isDefined(state.data)) {
                return null;
            }

            return state.data.step;
        };

        this.updateStep = function() {
            this.step = this.getStepFromState($state.current);
        };

        this.navigationAllowed = function(toState, formValid) {
            // Prevent user from navigating forwards if clicking the fishbone nav
            var toStep = this.getStepFromState(toState);

            if(toStep === null) { // step number is N/A. allow navigation because it is outside the register flow.
                return true;
            }

            if (toStep > this.step + 1 || (toStep === this.step + 1 && !formValid)) {
                return false;
            }
            return true;
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
