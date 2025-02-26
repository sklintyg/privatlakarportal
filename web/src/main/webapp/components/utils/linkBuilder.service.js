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

angular.module('privatlakareApp').factory('LinkBuilder',
    function($window, $log,
        messageService, APP_CONFIG, ObjectHelper) {
      'use strict';

      return {
        getExitLink: function(fromStateName, toStateName, userStatus, fromUrlPath, orgExitLink) { // jshint ignore:line
          var exitLink = {
            name: '',
            link: ''
          };

          // // Make no changes to the link if the user only wants to watch/just watched the terms.
          if(toStateName === 'app.minsida.terms' || fromStateName === 'app.minsida.terms') {
            return orgExitLink;
          }

          if (toStateName === 'app.minsida') {
            if (ObjectHelper.isEmpty(fromStateName) || fromStateName === 'app.boot') {
              // If we had no valid state before we came from an external site, assuming webcert for now.
              exitLink.name = messageService.getProperty('label.header.backtoapp');
              // Build exit link with specific path in external application if available.
              exitLink.link = ObjectHelper.isDefined(fromUrlPath) ? APP_CONFIG.webcertUrl + fromUrlPath : APP_CONFIG.webcertUrl;
              $log.debug('ExitLink:');
              $log.debug(exitLink.link);
            } else {
              switch (userStatus) {
              case 'AUTHORIZED':
                if (fromStateName === 'app.register.complete') {
                  exitLink.name = messageService.getProperty('label.header.backtocomplete');
                  exitLink.link = '/#/registrera/klar';
                }
                break;
              case 'NOT_AUTHORIZED':
              case 'WAITING_FOR_HOSP':
                exitLink.name = messageService.getProperty('label.header.backtocomplete');
                exitLink.link = '/#/registrera/vanta';
                break;
              }
            }
          } else { // complete, waiting. For start, register and every other state the link is hidden by logic.
            exitLink.name = messageService.getProperty('label.header.changeaccount');
            exitLink.link = '/#/minsida';
          }

          return exitLink;
        }
      };
    }
);
