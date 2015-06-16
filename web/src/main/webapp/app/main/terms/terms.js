angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';
        $stateProvider
            .state('app.main.terms', {
                url: 'terms',
                onEnter: ['$stateParams', '$state', '$modal', /*'$resource', 'Modal',*/
                    function($stateParams, $state, $modal/*, $resource, modalService*/) {

                        $('body').addClass('modalprinter');

                        $modal.open({
                            templateUrl: 'app/main/terms/terms.html',
                            controller: 'MainTermsCtrl',
                            size: 'md',
                            windowClass: 'modal-terms'
                        }).result.finally(function() { //jshint ignore:line
                            $('body').removeClass('modalprinter');

                            $state.go('^');
                        });

                    }
                ]
            });
    });
