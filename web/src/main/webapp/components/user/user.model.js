angular.module('privatlakareApp').factory('UserModel',
    function($window) {
        'use strict';

        return {
            reset: function() {
                this.name = 'Sandra Nilsson';
                this.personnummer = '191212121212';
                this.authenticationScheme = 'urn:inera:webcert:fake';
                return this;
            },
            logout: function() {
                if (this.authenticationScheme === 'urn:inera:webcert:fake') {
                    $window.location = '/logout';
                } else {
                    $window.location = '/saml/logout/';
                }
            }
        };
    }
);