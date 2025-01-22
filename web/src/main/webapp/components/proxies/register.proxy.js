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

angular.module('privatlakareApp').factory('RegisterProxy',
    function($http, $log, $q,
        ObjectHelper, RegisterModel, networkConfig) {
      'use strict';

      var timeout = networkConfig.registerTimeout;

      /*
       * Get the logged in privatlakare
       */
      function _getPrivatlakare() {

        var promise = $q.defer();

        var restPath = '/api/registration';
        $http.get(restPath, {timeout: timeout}).success(function(data) {
          $log.debug('registration - got data:');
          $log.debug(data);
          if (!ObjectHelper.isDefined(data)) {
            promise.reject({errorCode: data, message: 'invalid data'});
          } else {
            data = RegisterModel.convertToViewModel(data);
            promise.resolve(data);
          }
        }).error(function(data, status) {
          $log.error('error ' + status);
          // Let calling code handle the error of no data response
          if (data === null) {
            promise.reject({errorCode: data, message: 'no response'});
          } else {
            promise.reject(data);
          }
        });

        return promise.promise;
      }

      /*
       * Register a privatlakare
       */
      function _savePrivatlakare(registerModel) {

        var promise = $q.defer();

        // Create flat dto from model to send to backend
        var dto = RegisterModel.convertToDTO(registerModel);
        $log.debug('savePrivatlakare dto:');
        $log.debug(dto);

        if (dto === null) {
          $log.debug('Invalid dto. aborting save.');
          promise.reject({message: 'Invalid state'});
        } else {
          // POST
          var restPath = '/api/registration/save';
          $http.post(restPath, dto, {timeout: timeout}).success(function(data) {
            $log.debug('registration/save - got data:');
            $log.debug(data);
            promise.resolve(data);
          }).error(function(data, status) {
            $log.error('error ' + status);
            $log.debug('dto:');
            $log.debug(dto);
            // Let calling code handle the error of no data response
            if (data === null) {
              promise.reject({errorCode: data, message: 'no response'});
            } else {
              promise.reject(data);
            }
          });
        }

        return promise.promise;
      }

      /*
       * Register a privatlakare
       */
      function _registerPrivatlakare(registerModel, godkantMedgivandeVersion) {

        var promise = $q.defer();

        // Create flat dto from model to send to backend
        var dto = RegisterModel.convertToDTO(registerModel, godkantMedgivandeVersion);
        $log.debug('registerPrivatlakare dto:');
        $log.debug(dto);

        // POST
        var restPath = '/api/registration/create';
        $http.post(restPath, dto, {timeout: timeout}).success(function(data) {
          $log.debug('registration/create - got data:');
          $log.debug(data);
          promise.resolve(data);
        }).error(function(data, status) {
          $log.error('error ' + status);
          $log.debug('dto:');
          $log.debug(dto);
          // Let calling code handle the error of no data response
          if (data === null) {
            promise.reject({errorCode: data, message: 'no response'});
          } else {
            promise.reject(data);
          }
        });

        return promise.promise;
      }

      // Return public API for the service
      return {
        getPrivatlakare: _getPrivatlakare,
        savePrivatlakare: _savePrivatlakare,
        registerPrivatlakare: _registerPrivatlakare
      };
    });
