angular.module('privatlakareApp').factory('UserModel',
    function($window) {
        'use strict';

        var data = {};

        function _reset() {
            data.name = null;
            data.status = null;
            data.personnummer = null;
            data.authenticationScheme = null;
            data.fakeSchemeId = 'urn:inera:privatlakarportal:eleg:fake';
            data.loggedIn = false;
            data.nameFromPuService = false;
            data.nameUpdated = false;
            return data;
        }

        return {

            reset: _reset,
            init: function() {
                return _reset();
            },

            set: function(user) {
                data.name = user.name;
                data.personnummer = user.personalIdentityNumber;
                data.status = user.status;
                data.authenticationScheme = user.authenticationScheme;
                data.loggedIn = true;
                data.nameFromPuService = user.nameFromPuService;
                data.nameUpdated = user.nameUpdated;
            },
            get: function() {
                return data;
            },
            getStatusText: function() {
                var statusText;
                switch(data.status) {
                case 'AUTHORIZED':
                case 'NOT_AUTHORIZED':
                case 'WAITING_FOR_HOSP':
                    statusText = 'Privatläkare';
                    break;
                default:
                    statusText = 'Ej registrerad användare';
                }
                return statusText;
            },
            hasApplicationPermission: function() {
                return data.status === 'AUTHORIZED';
            },
            isRegistered: function() {
                return data.status === 'NOT_AUTHORIZED' || data.status === 'AUTHORIZED' || data.status === 'WAITING_FOR_HOSP';
            },
            fakeLogin: function() {
                if (data.authenticationScheme === data.fakeSchemeId) {
                    $window.location = '/welcome.html';
                }
            },
            logout: function() {
                if (data.authenticationScheme === data.fakeSchemeId) {
                    $window.location = '/logout';
                } else {
                    $window.location = '/saml/logout/';
                }
            }
        };
    }
);