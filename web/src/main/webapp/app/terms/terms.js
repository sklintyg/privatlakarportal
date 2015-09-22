angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';

        function openPortalTerms($stateParams, $state, dialogService) {

            $('body').addClass('modalprinter');

            dialogService.open({
                templateUrl: 'app/terms/terms.html',
                controller: 'MainTermsCtrl',
                size: 'md',
                windowClass: 'modal-terms'
            }).result.finally(function() { //jshint ignore:line
                $('body').removeClass('modalprinter');
                    if ($state.current.url === 'terms') {
                        $state.go('^');
                    }
            });
        }

        function closePortalTerms(dialogService) {
            dialogService.close();
        }

        $stateProvider
            .state('app.start.terms', {
                url: '/terms',
                views: {
                    'dialogs@': {
                        templateUrl: '/app/terms/terms.main.html',
                        controller: 'MainTermsCtrl'
                    }
                },
                params: {
                    terms: null,
                    termsData: null
                },
                data: {
                    rule: function(fromState) {
                        if (fromState.name !== 'app.start') {
                            return {
                                to: 'app.start'
                            };
                        }
                    }
                }
            }).state('app.register.step3.terms', {
                url: 'terms',
                onEnter: openPortalTerms,
                onExit: closePortalTerms,
                params: {
                    terms: null,
                    termsData: null
                },
                data: {
                    rule: function(fromState) {
                        if (fromState.name !== 'app.register.step3') {
                            return {
                                to: 'app.register.step3'
                            };
                        }
                    }
                }
            });

    });
