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
                UserModel.logout();
            };
        }
    ]);
