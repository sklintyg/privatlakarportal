/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
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

angular.module('privatlakareApp').factory('ObjectHelper',
    function() {
      'use strict';

      return {
        isDefined: function(value) {
          return value !== null && typeof value !== 'undefined';
        },
        isEmpty: function(value) {
          return value === null || typeof value === 'undefined' || value === '';
        },
        isFalsy: function(value) {
          return this.isEmpty(value) || value === 'false' || value === false;
        },
        returnJoinedArrayOrNull: function(value) {
          return value !== null && value !== undefined ? value.join(', ') : null;
        },
        valueOrNull: function(value) {
          return value !== null && value !== undefined ? value : null;
        },
        stringBoolToBool: function(value) {
          return value === true || value === 'true';
        },
        stringBoolToBoolUndefinedTrue: function(value) {
          if (!this.isDefined(value)) {
            return true;
          } else {
            return value === true || value === 'true';
          }
        }
      };
    }
);
