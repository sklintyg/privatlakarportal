angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';
        $stateProvider
            .state('app.eleg', {
                url: '/eleg',
                views: {
                    'content@app': {
                        templateUrl: '/app/about/eleg/eleg.html'
                    }
                }
            });
    });