angular.module('privatlakareApp')
    .controller('MainTermsCtrl', function($scope, $http, $state, $templateCache, $window, $log,
        TermsModel, TermsProxy) {
        'use strict';

        $scope.model = TermsModel.init();

        if ($state.params.termsData) {
            TermsModel.set($state.params.termsData);
        }
        else if ($state.params.terms) {
            TermsProxy.getTerms($state.params.terms).then(function(successData) {
                TermsModel.set(successData);
            }, function(errorData) {
                $log.debug('Failed to get terms.');
                $log.debug(errorData);
            });
        }

        $scope.dismiss = function() {
            $scope.$dismiss();
        };

        $scope.print = function() {
            $window.print();
        };
    });
