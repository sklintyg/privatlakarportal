/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

angular.module('privatlakareApp').factory('AppTermsModalModel',
    function($sessionStorage) {
      'use strict';

      var data = {};

      function _init() {
        if ($sessionStorage.appTermsModalModel) {
          data = $sessionStorage.appTermsModalModel;
        } else {
          $sessionStorage.appTermsModalModel = _reset();
        }
        return data;
      }

      function _reset() {
        data.modalInstance = null;
        data.options = {
          controller: null,
          modalBodyTemplateUrl: '/app/terms/terms.body.html',
          titleId: null,
          extraDlgClass: undefined,
          width: '600px',
          height: '90%',
          maxWidth: '600px',
          maxHeight: undefined,
          minWidth: undefined,
          minHeight: undefined,
          contentHeight: '100%',
          contentOverflowY: undefined,
          contentMinHeight: undefined,
          bodyOverflowY: 'scroll',
          buttons: [],
          showClose: true
        };

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