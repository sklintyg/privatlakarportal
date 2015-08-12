angular.module('privatlakareApp').factory('HospProxy',
        function($http, $log, $q) {
            'use strict';

            /*
             * Get hosp info about the logged in privatlakare
             */
            function _getHospInformation() {

                var promise = $q.defer();

                var restPath = '/api/registration/hospInformation';
                $http.get(restPath).success(function(data) {
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
