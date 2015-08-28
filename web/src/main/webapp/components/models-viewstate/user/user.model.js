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
            data.loggedIn = false;
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
                data.loggedIn = true;
                $sessionStorage.user = data;
            },
            get: function() {
                return data;
            },
            fakeLogin: function() {
                if (data.authenticationScheme === data.fakeSchemeId) {
                    $window.location = '/welcome.html';
                }
            },
            logout: function() {
                $sessionStorage.$reset();
                if (data.authenticationScheme === data.fakeSchemeId) {
                    $window.location = '/logout';
                } else {
                    $window.location = '/saml/logout/';
                }
            }
        };
    }
);