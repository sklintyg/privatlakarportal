angular.module('privatlakareApp').factory('TermsProxy',
        function($http, $log, $q,
               networkConfig) {
            'use strict';

            /*
             * Get user data for logged in user
             */
            function _getTerms() {
                var fnName = 'getTerms';
                $log.debug(fnName);

                var promise = $q.defer();

                var restPath = '/api/terms/webcert';
                $log.debug('REST call: ' + fnName + ' ' + restPath);
                $http.get(restPath, { timeout: networkConfig.defaultTimeout }).success(function(data) {
                    $log.debug(restPath + ' - got data:');
                    $log.debug(data);

                    if(typeof data.terms !== 'undefined') {
                        promise.resolve(data.terms);
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
                getTerms: _getTerms
            };
        });
