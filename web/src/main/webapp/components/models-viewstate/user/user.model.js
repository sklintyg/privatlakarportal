angular.module('privatlakareApp').factory('UserModel',
    function($window, $sessionStorage) {
        'use strict';

        var fakeSchemeId = 'urn:inera:privatlakarportal:fake';
        var data = {};

        function _reset() {
            $sessionStorage.user = null;
            data.name = null;
            data.status = null;
            data.personnummer = null;
            data.authenticationScheme = fakeSchemeId;
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
                data.status = user.status;
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