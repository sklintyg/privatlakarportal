/*
 * Copyright (C) 2018 Inera AB (http://www.inera.se)
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
angular.module('privatlakareApp').factory('ModalViewService',
    function($window, $state, $log, $timeout) {
        'use strict';

        function getWindowSize() {
            var docEl = document.documentElement,
                IS_BODY_ACTING_ROOT = docEl && docEl.clientHeight === 0;

            // Used to feature test Opera returning wrong values 
            // for documentElement.clientHeight. 
            function isDocumentElementHeightOff () {
                var d = document,
                    div = d.createElement('div');
                div.style.height = '2500px';
                d.body.insertBefore(div, d.body.firstChild);
                var r = d.documentElement.clientHeight > 2400;
                d.body.removeChild(div);
                return r;
            }

            if (typeof document.clientWidth === 'number') {
                return function () {
                    return { width: document.clientWidth, height: document.clientHeight };
                };
            } else if (IS_BODY_ACTING_ROOT || isDocumentElementHeightOff()) {
                var b = document.body;
                return function () {
                    return { width: b.clientWidth, height: b.clientHeight };
                };
            } else {
                return function () {
                    return { width: docEl.clientWidth, height: docEl.clientHeight };
                };
            }
        }
        
        function _updateModalBodyHeight(modalOptions){
            $timeout(function(){
                var header = angular.element('.modal-header').outerHeight();
                var footer = angular.element('.modal-footer').outerHeight();
                var modalcontent = angular.element('.modal-content').height();
                var modalBodyElement = angular.element('.modal-body');
                var modalHeight = getWindowSize()().height;

                if(header === null || footer === null || modalcontent === null || modalBodyElement === null) {
                    $log.info('content or DOM was not loaded yet. waiting...');
                } else {
                    var modalBody = modalHeight - header - footer - 110;
                    $log.info('header:' + header + ',footer:' + footer + ',modal:' + modalHeight + ',modalcontent:' +
                        modalcontent + ',modalBody:' + modalBody);
                    modalBodyElement.height(modalBody);
                }

                if(modalOptions !== undefined && modalOptions.bodyOverflowY !== undefined){
                    modalBodyElement.css('overflow-y', modalOptions.bodyOverflowY);
                } else {
                    $log.debug('invalid modal options.');
                }
            });
        }

        function _decorateModalScope($scope, $modalInstance) {

            _updateModalBodyHeight($scope.modal);

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

            $scope.modal.buttonAction = function(index){
                $scope.modal.buttons[index].clickFn($modalInstance, $scope.content);
            };

            var w = angular.element($window);
            w.bind('resize', function() {
                _updateModalBodyHeight($scope.modal);
            });

            $modalInstance.rendered.then(function() {
                $log.info('Modal rendered at: ' + new Date());
                _updateModalBodyHeight($scope.modal);
            }, function() {
                $log.info('Modal failed render? at: ' + new Date());
            });

            $modalInstance.result.then(function () {
                $log.info('Modal closed at: ' + new Date());
                popState();
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
                popState();
            });

            function popState() {
                if ($state.current.url === '/terms') {
                    $state.go('^');
                }
            }
        }

        return {
            updateModalBodyHeight: _updateModalBodyHeight,
            decorateModalScope: _decorateModalScope
        };
    });
