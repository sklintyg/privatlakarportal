angular.module('privatlakareApp').factory('UserModel',
    function() {
        'use strict';

        return {
            reset: function() {
                this.name = 'Sandra Nilsson';
                this.authenticationScheme = 'urn:inera:webcert:fake';
                return this;
            }
        };
    }
);