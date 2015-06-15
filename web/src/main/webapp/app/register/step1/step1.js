'use strict';

angular.module('privatlakareApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.register.step1', {
        url: '/1',
        views: {
            step: {
                templateUrl: 'app/register/step1/step1.html',
                controller: 'Step1Ctrl'
            }
        }
      });
  });