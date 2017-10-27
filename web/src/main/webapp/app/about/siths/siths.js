angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';
        $stateProvider
            .state('app.siths', {
                url: '/siths',
                views: {
                    'content@app': {
                        templateUrl: '/app/about/siths/siths.html'
                    }
                }
            });
    });