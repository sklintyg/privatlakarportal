angular.module('privatlakareApp').factory('UserModel',
    function($window, $sessionStorage) {
        'use strict';

        var data = {};

        function _reset() {
            $sessionStorage.user = null;
            data.name = null;
            data.status = null;
            data.personnummer = null;
            data.authenticationScheme = null;
            data.fakeSchemeId = 'urn:inera:privatlakarportal:eleg:fake';
            return data;
        }

        return {

            reset: _reset,
            init: function() {
                if($sessionStorage.user) {
                    data = $sessionStorage.user;
                    return data;
                }
                return _reset();
            },

            set: function(user) {
                data.name = user.name;
                data.personnummer = user.personalIdentityNumber;
                data.status = user.status;
                data.authenticationScheme = user.authenticationScheme;
                $sessionStorage.user = data;
            },
            get: function() {
                return data;
            },
            fakeLogin: function() {
                if (data.authenticationScheme === fakeSchemeId) {
                    $window.location = '/welcome.html';
                }
            },
            logout: function() {
                $sessionStorage.$reset();
                if (data.authenticationScheme === fakeSchemeId) {
                    $window.location = '/logout';
                } else {
                    $window.location = '/saml/logout/';
                }
            }
        };
    }
);