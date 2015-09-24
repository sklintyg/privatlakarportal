angular.module('privatlakareApp').factory('wcModalService',
    function($rootScope, $timeout, $window, $modal, $templateCache, $http, $q, $log) {
        'use strict';

        var modalInstance = null;
        var contentLoaded = false;

        function getTemplatePromise(options) {
            return options.template ? $q.when(options.template) :
                $http.get(angular.isFunction(options.templateUrl) ? (options.templateUrl)() : options.templateUrl,
                    {cache: $templateCache}).then(function (result) {
                        return result.data;
                    });
        }

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

                if(contentLoaded || header === null || footer === null || modalcontent === null || modalBodyElement === null) {
                    $log.info('content or DOM was not loaded yet. waiting...');
                } else {
                    modalBodyElement.height(modalBody);
                    modalBodyElement.css('overflow-y', modal.bodyOverflowY);
                }
            });
        }

        function open($scope, modal)
        {
            $scope.modal = modal;

            modalInstance = $modal.open(
                {
                    backdrop: 'static',
                    keyboard: false,
                    modalFade: false,

                    controller: modal.controller,
                    templateUrl: modal.templateUrl,
                    template: modal.template,
                    windowTemplateUrl: modal.windowTemplateUrl,
                    scope: $scope,
                    //size: size,   - overwritten by the extraDlgClass below (use 'modal-lg' or 'modal-sm' if desired)

                    extraDlgClass: modal.extraDlgClass,

                    width: modal.width,
                    height: modal.height,
                    maxWidth: modal.maxWidth,
                    maxHeight: modal.maxHeight,
                    minWidth: modal.minWidth,
                    minHeight: modal.minHeight
                });

            modalInstance.result.then(function ()
                {
                    $log.info('Modal closed at: ' + new Date());
                },
                function ()
                {
                    $log.info('Modal dismissed at: ' + new Date());
                });

            modalInstance.rendered.then(function() {
                $log.info('Modal rendered at: ' + new Date());
                modalBodyHeight(modal);
            }, function() {
                $log.info('Modal failed render? at: ' + new Date());
            });

            $scope.close = function($event){
                if ($event) {
                    $event.preventDefault();
                }
                modalInstance.dismiss('cancel');
            };

            $scope.cancel = function($event)
            {
                if ($event) {
                    $event.preventDefault();
                }
                modalInstance.dismiss('cancel');
            };

            return modalInstance;
        }

        function _open(options, $scope){

            var modalInstance = null;

            if($scope === undefined) {
                $scope = $rootScope.$new();
            }

            if(options === undefined){
                return;
            }

            var contentTemplate = 'components/commonDirectives/modal/wcModal.content.html',
                windowTemplate = 'components/commonDirectives/modal/wcModal.window.html';

            var modal = {
                controller: options.controller,
                titleId : options.titleId,
                extraDlgClass : options.extraDlgClass,
                width : options.width,
                height : options.height,
                maxWidth : options.maxWidth,
                maxHeight : options.maxHeight,
                minWidth : options.minWidth,
                minHeight : options.minHeight,
                contentHeight: options.contentHeight,
                contentOverflowY : options.contentOverflowY,
                contentMinHeight : options.contentMinHeight,
                bodyOverflowY: options.bodyOverflowY,
                templateUrl: options.templateUrl === undefined ? contentTemplate : options.templateUrl,
                windowTemplateUrl: options.windowTemplateUrl === undefined ? windowTemplate : options.windowTemplateUrl,
                showClose: options.showClose
            };

            if(modal.bodyOverflowY !== undefined){
                modal.bodyOuterStyle = 'height: 76%;' + 'overflow-y: ' + options.bodyOverflowY + ';';
            }

            if(options.buttons !== undefined && options.buttons.length > 0){
                modal.buttons = [];
                for(var i=0; i< options.buttons.length; i++){
                    var button = options.buttons[i];
                    var className = button.className === undefined ? 'btn-info' : button.className;
                    var mb = {
                        text:button.text,
                        id:button.id,
                        className:className,
                        clickFnName : 'modal.'+ button.name + '()',
                        clickFn : button.clickFn
                    };
                    modal[button.name] = button.clickFn;
                    modal.buttons.push(mb);
                }
            }

            modal.buttonAction = function(index){
                modal.buttons[index].clickFn();
            };

            if(modal !== undefined && modal.bodyOverflowY !== undefined) {
                var w = angular.element($window);
                w.bind('resize', function() {
                    modalBodyHeight(modal);
                });
            }

            var def = $q.defer();
            var templatePromise = def.promise;
            getTemplatePromise({templateUrl:modal.templateUrl}).then(function(modalTemplate){
                getTemplatePromise({templateUrl:options.modalBodyTemplateUrl}).then(function(modalBody){
                    // the compiling is done in ui bootstrap
                    // so lets just replace the placeholder in the modal templates html with that
                    // of the modal body
                    var res = modalTemplate.replace('<!-- modalBody -->', modalBody);
                    modal.template = res;
                    modal.templateUrl = undefined;
                    def.resolve();
                });
            });

            templatePromise.then(function(){
                $log.info('bodyOuterStyle: ' + modal.bodyOuterStyle);
                modalInstance = open($scope, modal);
                options.modalInstance = modalInstance;
                if(modal !== undefined && modal.bodyOverflowY !== undefined){
                    modalBodyHeight(modal);
                }

                options.contentLoadedPromise.then(function() {
                    $log.info('modal content loaded. updating size.');
                    contentLoaded = true;
                    if(modal !== undefined && modal.bodyOverflowY !== undefined){
                        modalBodyHeight(modal);
                    }
                }, function() {
                    $log.info('failed to load modal content');
                });
            });

            return modalInstance;
        }

        return {
            open: _open
        };
    });
