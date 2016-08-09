/*
 * Copyright (C) 2016 Inera AB (http://www.inera.se)
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

/* global console */

(function() {
    'use strict';


    // Add replaceAll function to all strings.
    String.prototype.replaceAll = function(f,r) { // jshint ignore:line
        return this.split(f).join(r);
    };
    // Globally configure jquery not to cache ajax requests.
    // Our other angular $http service driven requests have their own solution (using an interceptor)

    $.ajaxSetup({cache: false});

    var pp_app = angular.module('privatlakareApp',
        ['ui.bootstrap', 'ui.router', 'ngCookies', 'ngSanitize', 'ngAnimate', 'ngMockE2E', 'ngStorage']);

    var app = angular.module('showcase',
        ['ui.bootstrap', 'ui.router', 'ngCookies', 'ngSanitize', 'ngAnimate', 'ngMockE2E', 'privatlakareApp']);

    app.value('networkConfig', {
        defaultTimeout: 30000 // test: 1000
    });



    app.config(['$httpProvider', '$logProvider',
        function($httpProvider, $logProvider) {

            // Add cache buster interceptor
            $httpProvider.interceptors.push('http403ResponseInterceptor');

            // Enable debug logging
            $logProvider.debugEnabled(true);
        }]);

    // Global config of default date picker config (individual attributes can be
    // overridden per directive usage)
    app.constant('uibDatepickerPopupConfig', {
        altInputFormats: [],
        appendToBody: true,
        clearText: 'Rensa',
        closeOnDateSelection: true,
        closeText: 'OK',
        currentText: 'Idag',
        datepickerPopup: 'yyyy-MM-dd',
        datepickerPopupTemplateUrl: 'uib/template/datepickerPopup/popup.html',
        datepickerTemplateUrl: 'uib/template/datepicker/datepicker.html',
        html5Types: {
            date: 'yyyy-MM-dd',
            'datetime-local': 'yyyy-MM-ddTHH:mm:ss.sss',
            'month': 'yyyy-MM'
        },
        onOpenFocus: true,
        showButtonBar: true,
        placement: 'auto bottom-left'
    });

    // Inject language resources
    app.run(['$log', '$rootScope', '$window', '$location', '$state', '$q', '$httpBackend',
        function($log, $rootScope, $window, $location, $state, $q, $httpBackend) {

            $rootScope.lang = 'sv';
            $rootScope.DEFAULT_LANG = 'sv';

            //Kanske vi kan (i resp controller) sätta upp 'when' mockning så att direktiven kan köra som i en sandbox (Se exempel i arendehantering.controller.js)?
            // Detta kanske gör det möjligt att kunna laborera med ett direktivs alla funktioner som även kräver backendkommunikation.

           // /api/registration/omrade/13232
            $httpBackend.whenGET(/^\/api\/registration\/omrade\/*/).passThrough();
            $httpBackend.whenGET(/^\/api\/*/).respond(200);
            $httpBackend.whenPOST(/^\/api\/*/).respond(200);
            $httpBackend.whenPUT(/^\/api\/*/).respond(200);

            $httpBackend.whenGET(/^\/moduleapi\/*/).respond(200);
            $httpBackend.whenPOST(/^\/moduleapi\/*/).respond(200);
           // $httpBackend.whenPUT(/^\/moduleapi\/*/).respond(200);

            //Ev. templates skall få hämtas på riktigt
            $httpBackend.whenGET(/^.+\.html/).passThrough();

        }]);

    // Inject language resources
    pp_app.run(['$log', '$rootScope', '$window', '$location', '$state', '$q', '$httpBackend', 'messageService', 'messages',
        function($log, $rootScope, $window, $location, $state, $q, $httpBackend, messageService, messages) {

            messageService.addResources(ppMessages);// jshint ignore:line
            messageService.addResources(messages);// jshint ignore:line

            $rootScope.lang = 'sv';
            $rootScope.DEFAULT_LANG = 'sv';

        }]);

}());
