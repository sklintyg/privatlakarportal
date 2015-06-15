'use strict';

angular.module('privatlakareApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.register', {
        url: '/register',
        views: {
            'content@': {
                templateUrl: 'app/register/register.html',
                controller: 'RegisterCtrl'
            }
        }
      });
  });