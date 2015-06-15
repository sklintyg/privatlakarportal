angular.module('privatlakareApp').factory('RegisterModel',
    function() {
        'use strict';

        return {
            reset: function() {
                this.befattning = null;
                this.verksamhetensNamn = null;
                this.vardform = null;
                this.verksamhetstyp = null;
                this.arbetsplatskod = null;
                return this;
            }
        };
    }
);