angular.module('privatlakareApp')
    .config(function($stateProvider) {
        'use strict';

        /*
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
        }*/

        function printTerms($window, $timeout, $filter, messageService, content) {
            var head = '<!DOCTYPE html><html>' +
                '<head>' +
                '<link rel="stylesheet" href="/app/app.css" media="print">' +
                '<title>'+ messageService.getProperty(content.titleId) +'</title>' +
                '</head>';

            var body = '<body onload="window.print()">';
            if(content.logoImage !== undefined && content.logoImage !== null) {
                body += '<img class="pull-left" style="padding-bottom: 20px" src="/assets/images/'+ content.logoImage + '" />';
            }
            body += '<p style="clear:left;padding-bottom:50px;color:#535353">' +
                '<span style="padding-left:20px;padding-right:30px">Version: ' +
                content.terms.termsModel.version + '</span>' +
                '<span>Datum: ' + $filter('date')(content.terms.termsModel.date, 'yyyy-MM-dd') + '</span></p>' +
                '<h1 style="color: black;font-size: 2em">' + messageService.getProperty(content.titleId) + '</h1>' +
                '<p style="clear:left;padding-bottom: 10px">' + content.terms.termsModel.text + '</p>' +
                '<p style="clear:left;color:#535353;padding-top:50px">' + content.absUrl + '</p>' +
                '</body>';
            var footer = '</html>';

            var template = head + body + footer;

            var popupWin = null;
            if (navigator.userAgent.toLowerCase().indexOf('chrome') > -1) {
                popupWin = $window.open('', '_blank',
                    'width=400,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
                popupWin.window.focus();
                popupWin.document.write(template);
                $timeout(function() {
                    popupWin.close();
                }, 100);
                popupWin.onbeforeunload = function(event) {
                    popupWin.close();
                };
                popupWin.onabort = function(event) {
                    popupWin.document.close();
                    popupWin.close();
                };
            } else {
                popupWin = $window.open('', '_blank',
                    'width=800,scrollbars=yes,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
                popupWin.document.open();
                popupWin.document.write(template);
            }
            popupWin.document.close();

            return true;
        }

        function openTerms(modalModel, $state, $timeout, $filter, $window, wcModalService, messageService) {
            function popState() {
                if ($state.current.url === '/terms') {
                    $state.go('^');
                }
            }

            modalModel.options.buttons = [
                {
                    name: 'close',
                    text: 'common.close',
                    id: 'dismissBtn',
                    className: 'btn-success',
                    clickFn: function($modalInstance) {
                        $modalInstance.close();
                        popState();
                    }
                },
                {
                    name: 'print',
                    text: 'common.print',
                    id: 'printBtn',
                    clickFn: function($modalInstance, content) {
                        printTerms($window, $timeout, $filter, messageService, content);
                    }
                }
            ];

            modalModel.modalInstance = wcModalService.open(modalModel.options);
        }

        function openExternalAppTerms($state, $timeout, $filter, $window, wcModalService, AppTermsModalModel, messageService) {
            var modalModel = AppTermsModalModel.init();
            modalModel.options.controller = 'WebcertTermsCtrl';
            modalModel.options.titleId = 'label.modal.title.webcertvillkor';
            openTerms(modalModel, $state, $timeout, $filter, $window, wcModalService, messageService);
        }

        function openPortalTerms($state, $timeout, $filter, $window, wcModalService, AppTermsModalModel, messageService) {
            var modalModel = AppTermsModalModel.init();
            modalModel.options.controller = 'PortalTermsCtrl';
            modalModel.options.titleId = 'label.modal.title.portalvillkor';
            openTerms(modalModel, $state, $timeout, $filter, $window, wcModalService, messageService);
        }

        function closeTerms(AppTermsModalModel) {
            var modal = AppTermsModalModel.get().modalInstance;
            if(modal) {
                modal.dismiss('cancel');
            }
        }

        $stateProvider
            .state('app.start.terms', {
                url: '/terms',
                onEnter: openExternalAppTerms,
                onExit: closeTerms,
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
                url: '/terms',
                onEnter: openPortalTerms,
                onExit: closeTerms,
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
