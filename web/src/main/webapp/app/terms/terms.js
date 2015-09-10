angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';

        function openTerms($stateParams, $state, dialogService) {

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

        function closeTerms(dialogService) {
            dialogService.close();
        }

        $stateProvider
            .state('app.start.terms', {
                url: 'terms',
                onEnter: openTerms,
                onExit: closeTerms,
                params: {
                    terms: null,
                    termsData: null
                }
            }).state('app.register.step3.terms', {
                url: 'terms',
                onEnter: openTerms,
                onExit: closeTerms,
                params: {
                    terms: null,
                    termsData: null
                }
            });

    });
