angular.module('privatlakareApp')
    .config(function ($stateProvider) {
        'use strict';

        function showAbortDialog($state, dialogService) {
            dialogService.open({
                templateUrl: 'app/register/abort/abort.html',
                controller: 'RegisterAbortCtrl'
            }).result.finally(function() { //jshint ignore:line
                $state.go('^');
            });
        }

        $stateProvider
            .state('app.register.step1.abort', {
                url: '/avbryt',
                onEnter: showAbortDialog
            })
            .state('app.register.step2.abort', {
                url: '/avbryt',
                onEnter: showAbortDialog
            })
            .state('app.register.step3.abort', {
                url: '/avbryt',
                onEnter: showAbortDialog
            });
    });