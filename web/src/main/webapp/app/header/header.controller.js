angular.module('privatlakareApp').controller('HeaderController',
        function($scope, $window, $state,
            UserModel) {
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

            $scope.updateAccount = function() {
                $state.go('app.minsida');
            };

            $scope.logout = function() {
                UserModel.logout();
            };
        }
    );
