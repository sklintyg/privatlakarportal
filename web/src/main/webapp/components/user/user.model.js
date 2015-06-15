angular.module('privatlakareApp').factory('UserModel',
    function() {
        'use strict';

        return {
            reset: function() {
                this.name = 'Sandra Nilsson';
                this.personnummer = '191212121212';
                this.authenticationScheme = 'urn:inera:webcert:fake';
                return this;
            }
        };
    }
);