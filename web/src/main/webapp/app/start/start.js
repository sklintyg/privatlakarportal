angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';
        $stateProvider
            .state('app.start', {
                url: '/',
                views: {
                    'content@app': { templateUrl: 'app/start/start.html', controller: 'MainCtrl' }
                }
            });
    });