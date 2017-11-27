angular.module('privatlakareApp')
    .controller('MainCtrl', function($scope, $window) {
        'use strict';

        $scope.toWebcertLandingPage = function(){
            $window.location.href = 'https://webcert.intygstjanster.se/';
        };
    });
