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

angular.module('privatlakareApp').factory('UserProxy',
    function($http, $log, $q,
        networkConfig) {
      'use strict';

      /*
       * Get user data for logged in user
       */
      function _getUser() {
        $log.debug('getUser');

        var promise = $q.defer();

        var restPath = '/api/user';
        $log.debug('REST call: getUser ' + restPath);
        $http.get(restPath, {timeout: networkConfig.defaultTimeout}).success(function(data) {
          $log.debug(restPath + ' - got data:');
          $log.debug(data);

          if (typeof data.user !== 'undefined') {
            promise.resolve(data.user);
          } else {
            $log.debug('JSON response syntax error. user property required. Rejected.');
            promise.reject(null);
          }
        }).error(function(data, status) {
          $log.error('error ' + status);
          // Let calling code handle the error of no data response
          promise.reject(data);
        });

        return promise.promise;
      }

      // Return public API for the service
      return {
        getUser: _getUser
      };
    });
