angular.module('privatlakareApp')
    .controller('MainTermsCtrl', function($scope, $http, $templateCache, $window) {
        'use strict';

        $http.get('app/terms/terms_text.html', { cache: $templateCache }).
            success(function(data) {
                $scope.terms = data;
            });

        $scope.dismiss = function() {
            $scope.$dismiss();
        };

        $scope.print = function() {
            $window.print();
        };
    });
