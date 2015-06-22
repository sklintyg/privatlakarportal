angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';
        $stateProvider
            .state('app.complete', {
                url: '/klar',
                views: {
                    'content@': {
                        templateUrl: 'app/complete/complete.html',
                        controller: 'CompleteCtrl'
                    }
                }
            });
    });