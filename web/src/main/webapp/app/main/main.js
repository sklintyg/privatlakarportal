'use strict';

angular.module('privatlakareApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.main', {
        url: '/',
        views: {
            'content@': { templateUrl: 'app/main/main.html', controller: 'MainCtrl' }
        }
      });
  });