angular.module('privatlakareApp')
    .config(function($stateProvider, wcModalService, modalModel) {
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

        function openExternalAppTerms() {
            var modalOptions = {
                controller: 'MainTermsCtrl',
                /*content: $scope.terms,
                contentLoadedPromise: termsLoadedPromise,*/
                modalBodyTemplateUrl: 'app/terms/terms.body.html',
                titleId: 'label.appvillkor',
                extraDlgClass: undefined,
                width: '600px',
                height: '90%',
                maxWidth: '600px',
                maxHeight: undefined,
                minWidth: undefined,
                minHeight: undefined,
                contentHeight: '100%',
                contentOverflowY: undefined,
                contentMinHeight: undefined,
                bodyOverflowY: 'scroll',
                buttons: [
                    {
                        name: 'close',
                        clickFn: function() {
                            $scope.modalOptions.modalInstance.dismiss('cancel');
                            if ($state.current.url === '/terms') {
                                $state.go('^');
                            }
                        },
                        text: 'common.close',
                        id: 'dismissBtn',
                        className: 'btn-success'
                    },
                    {
                        name: 'print',
                        clickFn: function() {
                            var head = '<!DOCTYPE html><html>' +
                                '<head>' +
                                '<link rel="stylesheet" href="/app/app.css" media="print">' +
                                '<title>Användarvillkor för Webcert</title>' +
                                '</head>';

                            var body = '<body onload="window.print()">' +
                                '<img class="pull-left" style="padding-bottom: 20px" src="/assets/images/webcert_black.png" />' +
                                '<p style="clear:left;padding-bottom:50px;color:#535353">' +
                                '<span style="padding-left:20px;padding-right:30px">Version: ' +
                                $scope.modalOptions.content.version + '</span>' +
                                '<span>Datum: ' + $scope.modalOptions.content.datum + '</span></p>' +
                                '<h1 style="color: black;font-size: 2em">Användarvillkor för Webcert</h1>' +
                                '<p style="clear:left;padding-bottom: 10px">' + $scope.modalOptions.content.text + '</p>' +
                                '<p style="clear:left;color:#535353;padding-top:50px">' + $location.absUrl() + '</p>' +
                                '</body>';

                            var footer = '</html>';

                            var template = head + body + footer;

                            if (navigator.userAgent.toLowerCase().indexOf('chrome') > -1) {
                                var popupWin = window.open('', '_blank',
                                    'width=400,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
                                popupWin.window.focus();
                                popupWin.document.write(template);
                                setTimeout(function() {
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
                                var popupWin = window.open('', '_blank',
                                    'width=800,scrollbars=yes,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
                                popupWin.document.open();
                                popupWin.document.write(template);
                            }
                            popupWin.document.close();

                            return true;
                        },
                        text: 'common.print',
                        id: 'printBtn'
                    }
                ],
                showClose: false
            };

            modalModel.modalInstance = wcModalService.open(modalOptions);
        }

        function closeExternalAppTerms() {
            modalModel.modalInstance.dismiss('cancel');
        }

        $stateProvider
            .state('app.start.terms', {
                url: '/terms',
                onEnter: openExternalAppTerms,
                onExit: closeExternalAppTerms,
                views: {
                    'dialogs@': {
                        templateUrl: 'app/terms/terms.main.html',
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
