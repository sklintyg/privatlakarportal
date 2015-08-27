angular.module('privatlakareApp')
    .controller('MainTermsCtrl', function($scope, $http, $templateCache, $window,
        TermsProxy) {
        'use strict';

        TermsProxy.getTerms().then(function(successData) {
            $scope.terms = successData.text;
            $scope.version = successData.version;
        }, function(errorData) {

        });

        $scope.dismiss = function() {
            $scope.$dismiss();
        };

        $scope.print = function() {
            $window.print();
        };
    });
