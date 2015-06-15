angular.module('privatlakareApp').controller('HeaderController',
    ['$scope', '$window', 'UserModel',
        function($scope, $window, UserModel) {
            'use strict';

            //Expose 'now' as a model property for the template to render as todays date
            $scope.today = new Date();
            $scope.user = UserModel.reset();

            /**
             * Private functions
             */

            /**
             * Exposed scope interaction functions
             */

            $scope.logout = function() {
                if (UserModel.authenticationScheme === 'urn:inera:webcert:fake') {
                    $window.location = '/logout';
                } else {
                    $window.location = '/saml/logout/';
                }
            };
        }
    ]);
