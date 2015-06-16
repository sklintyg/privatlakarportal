angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';
        $stateProvider
            .state('app', {
                views: {
                    'header@': { templateUrl: 'app/header/header.html', controller: 'HeaderController' }
                }
            });
    });