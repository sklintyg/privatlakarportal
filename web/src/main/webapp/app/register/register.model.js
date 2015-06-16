angular.module('privatlakareApp').factory('RegisterModel',
    function(RegisterViewStateService) {
        'use strict';

        return {
            reset: function() {
                this.befattning = RegisterViewStateService.befattningList[0];
                this.verksamhetensNamn = null;
                this.vardform = RegisterViewStateService.vardformList[0];
                this.verksamhetstyp = RegisterViewStateService.verksamhetstypList[0];;
                this.arbetsplatskod = null;
                return this;
            }
        };
    }
);