angular.module('privatlakareApp').factory('UserModel',
    function($window, $sessionStorage) {
        'use strict';

        return {
            reset: function() {
                this.name = 'Sandra Nilsson';
                this.personnummer = '191212121212';
                this.authenticationScheme = 'urn:inera:webcert:fake';
                return this;
            },
            logout: function() {
                $sessionStorage.$reset();
                if (this.authenticationScheme === 'urn:inera:webcert:fake') {
                    $window.location = '/logout';
                } else {
                    $window.location = '/saml/logout/';
                }
            }
        };
    }
);