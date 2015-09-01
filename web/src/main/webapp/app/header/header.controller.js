angular.module('privatlakareApp').controller('HeaderController',
        function($scope, $window, $state, $log,
            UserModel) {
            'use strict';

            //Expose 'now' as a model property for the template to render as todays date
            $scope.today = new Date();
            $scope.userModel = UserModel;
            $scope.user = UserModel.init();
            $scope.statusText = '';

            $scope.$watch('user', function(newVal) {
                $log.debug('header controller - user status updated:');
                $log.debug(newVal.status);
                $scope.statusText = UserModel.getStatusText();
            }, true);

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
