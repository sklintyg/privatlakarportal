angular.module('privatlakareApp')
    .config(function ($stateProvider) {
        'use strict';
        $stateProvider
            .state('app.register.step2', {
                url: '/2',
                views: {
                    step: {
                        templateUrl: 'app/register/step2/step2.html',
                        controller: 'Step2Ctrl'
                    }
                }
            });
    });