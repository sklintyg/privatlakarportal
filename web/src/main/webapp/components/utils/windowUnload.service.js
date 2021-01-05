/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
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

angular.module('privatlakareApp').factory('WindowUnload',
    function($window, $log, UserModel) {
      'use strict';

      return {
        enable: function() {
          this.enabled = true;
        },
        disable: function() {
          this.enabled = false;
        },
        bindUnload: function($scope) {

          this.enable();

          var windowUnload = this;

          $window.onbeforeunload = function(event) {

            if (windowUnload.enabled) {

              if (!UserModel.get().loggedIn) {
                $log.debug('WindowUnload.bindUnload - not logged in - skipping dialog.');
                return;
              }

              var message = 'Om du lämnar sidan sparas inte dina ändringar.';
              if (typeof event === 'undefined') {
                event = $window.event;
              }
              if (event) {
                event.returnValue = message;
              }
              return message;
            }
          };

          $scope.$on('$destroy', function() {
            $window.onbeforeunload = null;
          });

        }
      };
    }
);
