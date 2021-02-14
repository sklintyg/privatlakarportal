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

angular.module('privatlakareApp').factory('TermsModel',
    function($sessionStorage) {
      'use strict';

      var data = {};

      function _init() {
        if ($sessionStorage.termsModel) {
          data = $sessionStorage.termsModel;
        } else {
          $sessionStorage.termsModel = _reset();
        }
        return data;
      }

      function _reset() {

        data.text = null;
        data.date = null;
        data.version = null;

        return data;
      }

      function _set(newData) {
        data.text = newData.text;
        data.date = newData.date;
        data.version = newData.version;
      }

      function _get() {
        return data;
      }

      function _isEmpty() {
        return !data.text;
      }

      return {
        init: _init,
        reset: _reset,
        set: _set,
        get: _get,
        isEmpty: _isEmpty
      };
    }
);