angular.module('privatlakareApp').factory('RegisterModel',
    function($sessionStorage, RegisterViewStateService) {
        'use strict';

        var data = {};

        function _init() {
            if ($sessionStorage.registerModel) {
                data = $sessionStorage.registerModel;
            }
            else {
                $sessionStorage.registerModel = _reset();
            }
            return data;
        }

        function _reset() {
            // Step 1
            data.befattning = null;
            data.verksamhetensNamn = null;
            data.agandeForm = 'Privat';
            data.vardform = RegisterViewStateService.vardformList[0];
            data.verksamhetstyp = null;
            data.arbetsplatskod = null;

            // Step 2
            data.telefonnummer = null;
            data.epost = null;
            data.epost2 = null;
            data.adress = null;
            data.postnummer = null;
            data.postort = null;
            data.kommun = null;
            return data;
        }

        return {
            init: _init
        };
    }
);