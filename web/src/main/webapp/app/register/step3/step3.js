angular.module('privatlakareApp')
    .config(function ($stateProvider) {
        'use strict';
        $stateProvider
            .state('app.register.step3', {
                url: '/3',
                views: {
                    step: {
                        templateUrl: 'app/register/step3/step3.html',
                        controller: 'Step3Ctrl'
                    }
                }
            });
    });