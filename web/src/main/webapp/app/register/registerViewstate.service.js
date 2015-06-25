angular.module('privatlakareApp').service('RegisterViewStateService',
    function($state) {
        'use strict';

        this.reset = function() {
            this.step = 1;

            this.pasteErrorEpost = false;
            this.pasteErrorEpost2 = false;

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

        this.reset();
    }
);
