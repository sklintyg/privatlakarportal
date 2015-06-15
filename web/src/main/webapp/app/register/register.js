'use strict';

angular.module('privatlakareApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.register', {
        url: '/registrera',
        views: {
            'content@': {
                templateUrl: 'app/register/register.html',
                controller: 'RegisterCtrl'
            }
        }
      });
  });