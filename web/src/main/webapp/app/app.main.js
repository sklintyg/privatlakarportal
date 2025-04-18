/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

var app = angular.module('privatlakareApp', [
  'ngAnimate',
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ngStorage',
  'ngMessages',
  'ui.router',
  'ui.bootstrap'
]);

deferredBootstrapper.bootstrap({
  element: document.body,
  module: 'privatlakareApp',
  resolve: {
    APP_CONFIG: ['$http', function($http) {
      'use strict';
      return $http.get('/api/config');
    }],
    LINKS: ['$http', function($http) {
      'use strict';
      return $http.get('/api/config/links');
    }],
    USER_DATA: ['$http', function($http) {
      'use strict';
      return $http.get('/api/user');
    }]
  }
});

app.value('networkConfig', {
  defaultTimeout: 30000,
  regionTimeout: 30000,
  registerTimeout: 30000,
  hospTimeout: 30000
});

app.config(function($stateProvider, $urlRouterProvider, $locationProvider, $tooltipProvider, $httpProvider,
    http403ResponseInterceptorProvider) {
  'use strict';

  // Default route
  $urlRouterProvider.otherwise('/');

  // Use /#/ syntax. True = regular / syntax
  $locationProvider.html5Mode(false);

  // Tooltip config
  $tooltipProvider.setTriggers({
    'show': 'hide'
  });

  // Configure 403 interceptor provider
  http403ResponseInterceptorProvider.setRedirectUrl('/');
  $httpProvider.interceptors.push('http403ResponseInterceptor');

  // Add replaceAll function to all strings.
  String.prototype.replaceAll = function(f, r) { // jshint ignore:line
    return this.split(f).join(r);
  };

});

// Global config of default date picker config (individual attributes can be
// overridden per directive usage)
app.constant('datepickerConfig', {
  formatDay: 'dd',
  formatMonth: 'MMMM',
  formatYear: 'yyyy',
  formatDayHeader: 'EEE',
  formatDayTitle: 'MMMM yyyy',
  formatMonthTitle: 'yyyy',
  datepickerMode: 'day',
  minMode: 'day',
  maxMode: 'year',
  showWeeks: true,
  startingDay: 1,
  yearRange: 20,
  minDate: null,
  maxDate: null
});

// Global config of default date picker config (individual attributes can be
// overridden per directive usage)

app.constant('datepickerPopupConfig', {
  datepickerPopup: 'yyyy-MM-dd',
  currentText: 'Idag',
  clearText: 'Rensa',
  closeText: 'OK',
  closeOnDateSelection: true,
  appendToBody: false,
  showButtonBar: true
});

// Inject language resources
app.run(
    function($log, $rootScope, $state, $window, $animate,
        messageService, dynamicLinkService, UserProxy, UserModel, USER_DATA, LINKS) {
      'use strict';

      // For testability
      $window.disableAnimations = function() {
        $animate.enabled(false);
      };

      // Always scroll to top
      $rootScope.$on('$stateChangeSuccess', function() {
        $('html, body').animate({scrollTop: 0}, 200);
      });

      $rootScope.lang = 'sv';
      $rootScope.DEFAULT_LANG = 'sv';

      // Get logged in user
      UserModel.init();
      UserModel.set(USER_DATA.user);

      /* jshint -W117 */
      messageService.addResources(ppMessages);// jshint ignore:line
      messageService.addLinks(LINKS);

      dynamicLinkService.addLinks(LINKS);

      $window.animations = 0;
      $window.doneLoading = false;
      $window.dialogDoneLoading = true;
      $window.rendered = true;
      $window.saving = false;
      $window.hasRegistered = false;
      // watch the digest cycle
      $rootScope.$watch(function() {
        if ($window.hasRegistered) {
          return;
        }
        $window.hasRegistered = true;
        // Note that we're using a private Angular method here (for now)
        $rootScope.$$postDigest(function() {
          $window.hasRegistered = false;
        });
      });

      $rootScope.$on('$stateChangeStart',
          function(event, toState, toParams, fromState/*, fromParams*/) {
            $window.doneLoading = false;

            if (toState.data && angular.isFunction(toState.data.rule)) {
              var result = toState.data.rule(fromState);
              if (result && result.to) {
                event.preventDefault();
                $state.go(result.to, result.params);
              }
            }
          });

      $rootScope.$on('$stateNotFound',
          function(/*event, unfoundState, fromState, fromParams*/) {
          });

      $rootScope.$on('$stateChangeSuccess',
          function(/*event, toState, toParams, fromState, fromParams*/) {
            $window.doneLoading = true;
          });

      $rootScope.$on('$stateChangeError',
          function(event, toState/*, toParams, fromState, fromParams, error*/) {
            $log.log('$stateChangeError');
            $log.log(toState);
          });
    });
