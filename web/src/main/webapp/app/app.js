angular.module('privatlakareApp')
    .config(function($stateProvider, $httpProvider, http403ResponseInterceptorProvider) {
        'use strict';
        $stateProvider
            .state('app', {
                views: {
                    'app@': { templateUrl: 'app/app.html', controller: 'AppCtrl' },
                    'header@': { templateUrl: 'app/header/header.html', controller: 'HeaderController' }
                }
            });

        // Configure 403 interceptor provider
        http403ResponseInterceptorProvider.setRedirectUrl('/');
        $httpProvider.interceptors.push('http403ResponseInterceptor');
    });