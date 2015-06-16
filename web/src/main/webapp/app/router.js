'use strict';

angular.module('privatlakareApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('app', {
        views: {
            'header@': { templateUrl: 'app/header/header.html', controller: 'HeaderController' }
        }
      });
  });