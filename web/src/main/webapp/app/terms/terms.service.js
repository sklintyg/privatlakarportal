angular.module('privatlakareApp').factory('TermsService',
    function($state, $log, $q,
        TermsModel, TermsProxy) {
        'use strict';

        function _loadTerms() {

            var termsLoadDeferred = $q.defer();

            var content = {
                termsModel: TermsModel.init(),
                loading: false,
                termsPromise: termsLoadDeferred.promise
            };

            if ($state.params.termsData) {
                TermsModel.set($state.params.termsData);
            }
            else if ($state.params.terms) {
                content.loading = true;
                TermsProxy.getTerms($state.params.terms).then(function(successData) {
                    content.loading = false;
                    TermsModel.set(successData);
                    $log.info('modal content loaded. updating size.');
                    termsLoadDeferred.resolve();
                }, function(errorData) {
                    content.loading = false;
                    $log.debug('Failed to get terms.');
                    $log.debug(errorData);
                    termsLoadDeferred.resolve();
                });
            }

            return content;
        }

        return {
            loadTerms: _loadTerms
        };
    });
