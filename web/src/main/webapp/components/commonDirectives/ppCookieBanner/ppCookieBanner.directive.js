angular.module('privatlakareApp').directive('ppCookieBanner',

    function($window) {
        'use strict';

        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'components/commonDirectives/ppCookieBanner/ppCookieBanner.directive.html',
            controller: function($scope, $timeout) {
                $scope.isOpen = false;
                $scope.showDetails = false;

                function cookieConsentGiven() {
                    return $window.localStorage && $window.localStorage.getItem('pp-cookie-consent-given') === '1';
                }


                $timeout(function() {
                    if (!cookieConsentGiven()) {
                        $scope.isOpen = true;
                    }
                }, 500);


                $scope.onCookieConsentClick = function() {
                    $scope.isOpen = false;
                    if ($window.localStorage) {
                        $window.localStorage.setItem('pp-cookie-consent-given', '1');
                    }

                };
            }
        };
    });