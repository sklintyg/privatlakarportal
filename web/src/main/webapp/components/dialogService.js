angular.module('privatlakareApp').factory('dialogService',
    function($modal, $timeout, $window) {
        'use strict';

        var _modal = null;

        function _open(options) {
            _modal = $modal.open(options);
            _runOnDialogDoneLoading(_modal);
            return _modal;
        }

        function _close() {
            if (_modal) {
                _modal.dismiss();
                _modal = null;
            }
        }

        function _runOnDialogDoneLoading(modal, callback) {

            $window.dialogDoneLoading = false;

            if (!callback) {
                callback = function() {
                    $window.dialogDoneLoading = true;
                };
            }

            modal.opened.then(function() {
                function waitForModalToExistAndRunCallbackWhenTransitionIsDone() {
                    var modalDialog = $('[modal-window]');
                    if (modalDialog && modalDialog.hasClass('in')) {
                        modalDialog.one('transitionend webkitTransitionEnd oTransitionEnd MSTransitionEnd',
                            callback);
                    } else {
                        $timeout(waitForModalToExistAndRunCallbackWhenTransitionIsDone, 100);
                    }
                }

                $timeout(waitForModalToExistAndRunCallbackWhenTransitionIsDone);

            }, function() {
                // Failed to open the modal -> finished loading
                callback();
            });
        }

        // Return public API for the service
        return {
            close: _close,
            open: _open,
            runOnDialogDoneLoading: _runOnDialogDoneLoading
        };
    });
