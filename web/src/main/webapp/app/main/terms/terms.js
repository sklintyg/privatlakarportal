'use strict';

angular.module('privatlakareApp')
    .config(function($stateProvider) {
        $stateProvider
            .state('app.main.terms', {
                url: 'terms',
                onEnter: ['$stateParams', '$state', '$modal', '$resource', 'Modal',
                    function($stateParams, $state, $modal, $resource, modalService) {

                        $('body').addClass('modalprinter');

                        $modal.open({
                            templateUrl: 'app/main/terms/terms.html',
                            controller: 'MainTermsCtrl',
                            size: 'md',
                            windowClass: 'modal-terms'
                        }).result.finally(function() {
                            $('body').removeClass('modalprinter');

                            $state.go('^');
                        });

                    }
                ]
            });
    });
