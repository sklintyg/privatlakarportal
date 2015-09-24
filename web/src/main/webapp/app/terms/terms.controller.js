angular.module('privatlakareApp')
    .controller('MainTermsCtrl', function($scope, $http, $state, $templateCache, $window, $log, $location, $q,
        TermsModel, TermsProxy) {
        'use strict';

        $scope.terms = TermsModel.init();
        $scope.loading = {
            terms: false
        };

        var termsLoadedDeferred = $q.defer();
        var termsLoadedPromise = termsLoadedDeferred.promise;

        if ($state.params.termsData) {
            TermsModel.set($state.params.termsData);
        }
        else if ($state.params.terms) {
            $scope.loading.terms = true;
            TermsProxy.getTerms($state.params.terms).then(function(successData) {
                $scope.loading.terms = false;
                TermsModel.set(successData);
                termsLoadedDeferred.resolve();
            }, function(errorData) {
                $scope.loading.terms = false;
                $log.debug('Failed to get terms.');
                $log.debug(errorData);
                termsLoadedDeferred.reject();
            });
        }

        $scope.dismiss = function() {
            $scope.$dismiss();
        };

        $scope.print = function() {
            $window.print();
        };

    });
