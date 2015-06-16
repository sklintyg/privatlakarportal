var app = angular.module('privatlakareApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ui.router',
  'ui.bootstrap'
]);

app.config(function ($stateProvider, $urlRouterProvider, $locationProvider) {
    'use strict';
    $urlRouterProvider.otherwise('/');
    $locationProvider.html5Mode(false);
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
app.run(['$log', '$rootScope', '$window', 'messageService', /*'UserModel',*/
    function($log, $rootScope, $window, messageService/*, UserModel*/) {
        'use strict';

        $rootScope.lang = 'sv';
        $rootScope.DEFAULT_LANG = 'sv';
        //UserModel.setUserContext(MODULE_CONFIG.USERCONTEXT);

        /* jshint -W117 */
        messageService.addResources(ppMessages);// jshint ignore:line

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
            function(/*event, toState, toParams, fromState, fromParams*/){
                $window.doneLoading = false;
            });

        $rootScope.$on('$stateNotFound',
            function(/*event, unfoundState, fromState, fromParams*/){
            });

        $rootScope.$on('$stateChangeSuccess',
            function(/*event, toState, toParams, fromState, fromParams*/){
                $window.doneLoading = true;
            });

        $rootScope.$on('$stateChangeError',
            function(event, toState/*, toParams, fromState, fromParams, error*/){
                $log.log('$stateChangeError');
                $log.log(toState);
            });
    }]);
