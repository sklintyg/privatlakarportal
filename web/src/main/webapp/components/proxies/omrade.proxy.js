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

angular.module('privatlakareApp').factory('OmradeProxy',
    function($http, $log, $q,
        networkConfig) {
      'use strict';

      /*
       * Get region info for a postnummer
       */
      function _getOmradeList(postnummer) {
        $log.debug('getOmradeList');

        var promise = $q.defer();

        if (typeof postnummer !== 'string') {
          $log.debug('invalid parameter. postnummer must be a string of length 5 or 6.');
          promise.reject(null);
        } else {

          // Clean input
          postnummer = postnummer.replaceAll(' ', '');
          postnummer = postnummer.trim();
          if (postnummer.length !== 5) {
            $log.debug('invalid parameter. postnummer too short or too long. must be length 5 after trimming.');
            promise.reject(null);
          } else {
            var restPath = '/api/registration/omrade/' + postnummer;
            $log.debug('REST call: getOmradeList - ' + restPath);
            $http.get(restPath, {timeout: networkConfig.regionTimeout}).success(function(data) {
              $log.debug('registration/omrade - got data:');
              $log.debug(data);

              if (typeof data !== 'undefined' && typeof data.omradeList !== 'undefined') {
                promise.resolve(data.omradeList);
              } else {
                $log.debug('JSON response syntax error. omradeList property required. Rejected.');
                promise.reject(null);
              }

            }).error(function(data, status) {
              $log.error('error ' + status);
              // Let calling code handle the error of no data response
              promise.reject(data);
            });
          }
        }

        return promise.promise;
      }

      // Return public API for the service
      return {
        getOmradeList: _getOmradeList
      };
    });
