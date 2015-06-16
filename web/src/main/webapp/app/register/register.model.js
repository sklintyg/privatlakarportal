angular.module('privatlakareApp').factory('RegisterModel',
    function(RegisterViewStateService) {
        'use strict';

        return {
            reset: function() {
                // Step 1
                this.befattning = RegisterViewStateService.befattningList[0];
                this.verksamhetensNamn = null;
                this.agandeForm = 'Privat';
                this.vardform = RegisterViewStateService.vardformList[0];
                this.verksamhetstyp = RegisterViewStateService.verksamhetstypList[0];
                this.arbetsplatskod = null;

                // Step 2
                this.telefonnummer = null;
                this.epost = null;
                this.epost2 = null;
                this.adress = null;
                this.postnummer = null;
                this.postort = null;
                this.kommun = null;
                return this;
            }
        };
    }
);