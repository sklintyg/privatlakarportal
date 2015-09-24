angular.module('privatlakareApp').factory('TermsModel',
    function($sessionStorage) {
        'use strict';

        var data = {};

        function _init() {
            if ($sessionStorage.appTermsModalModel) {
                data = $sessionStorage.appTermsModalModel;
            }
            else {
                $sessionStorage.appTermsModalModel = _reset();
            }
            return data;
        }

        function _reset() {
            data.modalInstance = null;
            data.options = null;
            return data;
        }

        function _set(newData) {
            data.modalInstance = newData.modalInstance;
            data.options = newData.options;
        }

        function _get() {
            return data;
        }

        return {
            init: _init,
            reset: _reset,
            set: _set,
            get: _get
        };
    }
);