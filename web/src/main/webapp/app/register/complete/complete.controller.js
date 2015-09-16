angular.module('privatlakareApp')
    .controller('CompleteCtrl', function($scope, $window,
        APP_CONFIG, UserModel) {
        'use strict';

        UserModel.get().fire

        $scope.goToApp = function() {
            $window.location = APP_CONFIG.webcertStartUrl;
        };
    });
