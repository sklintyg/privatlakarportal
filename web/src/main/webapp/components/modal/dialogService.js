/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
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

angular.module('privatlakareApp').factory('dialogService',
    function($animate, $modal, $rootScope, $timeout, $window) {
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
              $animate.on('leave', $('.modal'), function() {
                $window.dialogDoneLoading = true;
              });
            } else {
              $timeout(waitForModalToExistAndRunCallbackWhenTransitionIsDone, 100);
            }
          }

          $timeout(waitForModalToExistAndRunCallbackWhenTransitionIsDone);

        }, function() {
          // Failed to open the modal -> finished loading
          callback();
        });

        modal.result.then(function() {
          $window.dialogDoneLoading = false;
        }, function() {
          $window.dialogDoneLoading = false;
        });
      }

      // Return public API for the service
      return {
        close: _close,
        open: _open,
        runOnDialogDoneLoading: _runOnDialogDoneLoading
      };
    });
