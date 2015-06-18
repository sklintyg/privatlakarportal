angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';

        function openTerms($stateParams, $state, $modal) {

            $('body').addClass('modalprinter');

            $modal.open({
                templateUrl: 'app/terms/terms.html',
                controller: 'MainTermsCtrl',
                size: 'md',
                windowClass: 'modal-terms'
            }).result.finally(function() { //jshint ignore:line
                    $('body').removeClass('modalprinter');
                    $state.go('^');
                });
        }

        $stateProvider
            .state('app.main.terms', {
                url: 'terms',
                onEnter: openTerms
            }).state('app.register.step3.terms', {
                url: 'terms',
                onEnter: openTerms
            });

    });
