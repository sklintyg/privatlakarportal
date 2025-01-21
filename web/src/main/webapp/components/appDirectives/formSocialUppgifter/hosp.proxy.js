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

angular.module('privatlakareApp').factory('HospProxy',
    function($http, $log, $q,
        networkConfig) {
      'use strict';

      /*
       * Get hosp info about the logged in privatlakare
       */
      function _getHospInformation() {

        var promise = $q.defer();

        /*
        *
        * In case we need a global timeout config in the future it ca nbe added as a httpinterceptor
        *
        *  angular.module('yourapp')
         .factory('timeoutHttpIntercept', function ($rootScope, $q) {
         return {
         'request': function(config) {
         config.timeout = 10000;
         return config;
         }
         };
         });
         And then in .config inject $httpProvider and do this:

         $httpProvider.interceptors.push('timeoutHttpIntercept');
        *
        * */

        var restPath = '/api/registration/hospInformation';
        $http.get(restPath, {timeout: networkConfig.hospTimeout}).success(function(data) {
          $log.debug('registration/hospInformation - got data:');
          $log.debug(data);
          promise.resolve(data.hospInformation);
        }).error(function(data, status) {
          $log.error('error ' + status);
          // Let calling code handle the error of no data response
          promise.reject(data);
        });

        return promise.promise;
      }

      // Return public API for the service
      return {
        getHospInformation: _getHospInformation
      };
    });
