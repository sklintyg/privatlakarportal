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
            data.agarForm = 'Privat';
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
            data.lan = null;
            return data;
        }

        function _set(newData) {
            data = angular.copy(newData);
        }

        function _get() {
            return data;
        }
        
        function _convertToViewModel(dto) {
            var viewModel = angular.copy(dto);
            viewModel.registration.befattning = { id: viewModel.registration.befattning };
            viewModel.registration.vardform = { id: viewModel.registration.vardform };
            viewModel.registration.verksamhetstyp = { id: viewModel.registration.verksamhetstyp };
            viewModel.registration.epost2 = viewModel.registration.epost;
            return viewModel;
        }

        function _convertToDTO(viewModel) {
            var dto = { registration: angular.copy(viewModel) };
            dto.registration.befattning = dto.registration.befattning.id;
            dto.registration.vardform = dto.registration.vardform.id;
            dto.registration.verksamhetstyp = dto.registration.verksamhetstyp.id;
            delete dto.registration.epost2;
            return dto;
        }
        
        return {
            init: _init,
            reset: _reset,
            set: _set,
            get: _get,
            convertToViewModel: _convertToViewModel,
            convertToDTO: _convertToDTO
        };
    }
);