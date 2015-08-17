angular.module('privatlakareApp').factory('RegisterProxy',
        function($http, $log, $q,
            ObjectHelper) {
            'use strict';

            /*
             * Get the logged in privatlakare
             */
            function _getPrivatlakare() {

                var promise = $q.defer();

                var restPath = '/api/registration';
                $http.get(restPath).success(function(data) {
                    $log.debug('registration - got data:');
                    $log.debug(data);
                    if(!ObjectHelper.isDefined(data)) {
                        promise.reject(data);
                    } else {
                        data.registration.befattning = { id: data.registration.befattning };
                        data.registration.vardform = { id: data.registration.vardform };
                        data.registration.verksamhetstyp = { id: data.registration.verksamhetstyp };
                        data.registration.epost2 = data.registration.epost;
                        promise.resolve(data.registration);
                    }
                }).error(function(data, status) {
                    $log.error('error ' + status);
                    // Let calling code handle the error of no data response
                    promise.reject(data);
                });

                return promise.promise;
            }

            /*
             * Register a privatlakare
             */
            function _registerPrivatlakare(RegisterModel) {

                var promise = $q.defer();

                // Create flat dto from model to send to backend
                var dto = { registration: angular.copy(RegisterModel) };
                dto.registration.befattning = RegisterModel.befattning.id;
                dto.registration.vardform = RegisterModel.vardform.id;
                dto.registration.verksamhetstyp = RegisterModel.verksamhetstyp.id;
                $log.debug('registerPrivatlakare dto:');
                $log.debug(dto);

                // POST
                var restPath = '/api/registration/create';
                $http.post(restPath, dto).success(function(data) {
                    $log.debug('registration/create - got data:');
                    $log.debug(data);
                    promise.resolve(data);
                }).error(function(data, status) {
                    $log.error('error ' + status);
                    $log.debug('dto:');
                    $log.debug(dto);
                    // Let calling code handle the error of no data response
                    promise.reject(data);
                });

                return promise.promise;
            }

            // Return public API for the service
            return {
                getPrivatlakare: _getPrivatlakare,
                registerPrivatlakare: _registerPrivatlakare
            };
        });
