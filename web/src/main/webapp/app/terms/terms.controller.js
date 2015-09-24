angular.module('privatlakareApp')
    .controller('MainTermsCtrl', function($scope, $http, $state, $templateCache, $window, $log, $location, $q, $timeout,
        TermsModel, TermsProxy, $modalInstance) {
        'use strict';

        var contentLoaded = false;
        $scope.content = TermsModel.init();
        $scope.content.loading = false;
        $scope.content.absUrl = $location.absUrl();

        if ($state.params.termsData) {
            TermsModel.set($state.params.termsData);
        }
        else if ($state.params.terms) {
            $scope.content.loading = true;
            TermsProxy.getTerms($state.params.terms).then(function(successData) {
                $scope.content.loading = false;
                TermsModel.set(successData);
                $log.info('modal content loaded. updating size.');
                contentLoaded = true;
                modalBodyHeight($scope.modal);
            }, function(errorData) {
                $scope.content.loading = false;
                $log.debug('Failed to get terms.');
                $log.debug(errorData);
                modalBodyHeight($scope.modal);
            });
        }

        $scope.close = function($event){
            if ($event) {
                $event.preventDefault();
            }
            $modalInstance.close('cancel');
        };

        $scope.cancel = function($event)
        {
            if ($event) {
                $event.preventDefault();
            }
            $modalInstance.dismiss('cancel');
        };

        function modalBodyHeight(modal){
            $timeout(function(){
                var header = angular.element('.modal-header').outerHeight();
                var footer = angular.element('.modal-footer').outerHeight();
                var modalcontent = angular.element('.modal-content').height();
                var modalBodyElement = angular.element('.modal-body');
                var modalHeight = angular.element(document).height();
                var modalBody = modalHeight - header - footer - 110;
                $log.info('header:' + header + ',footer:' + footer + ',modal:' + modalHeight + ',modalcontent:' +
                    modalcontent + ',modalBody:' + modalBody + ',contentLoaded:' + contentLoaded);

                if(!contentLoaded || header === null || footer === null || modalcontent === null || modalBodyElement === null) {
                    $log.info('content or DOM was not loaded yet. waiting...');
                }
                modalBodyElement.height(modalBody);
                if(modal !== undefined && modal.bodyOverflowY !== undefined){
                    modalBodyElement.css('overflow-y', modal.bodyOverflowY);
                } else {
                    $log.debug('invalid modal options.');
                }
            });
        }

        $scope.modal.buttonAction = function(index){
            $scope.modal.buttons[index].clickFn($modalInstance, $scope.content);
        };

        var w = angular.element($window);
        w.bind('resize', function() {
            modalBodyHeight($scope.modal);
        });

        $modalInstance.rendered.then(function() {
            $log.info('Modal rendered at: ' + new Date());
            modalBodyHeight($scope.modal);
        }, function() {
            $log.info('Modal failed render? at: ' + new Date());
        });

        $modalInstance.result.then(function () {
            $log.info('Modal closed at: ' + new Date());
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });

    });
