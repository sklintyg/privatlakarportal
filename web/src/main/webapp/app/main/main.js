angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';
        $stateProvider
            .state('app.main', {
                url: '/',
                views: {
                    'content@': { templateUrl: 'app/main/main.html', controller: 'MainCtrl' }
                }
            });
    });