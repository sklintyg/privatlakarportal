'use strict';

angular.module('privatlakareApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('app.minsida', {
        url: '/minsida',
            views: {
                'content@app': {
                    templateUrl: 'app/minsida/minsida.html',
                    controller: 'MinsidaCtrl'
                }
            }
      });
  });