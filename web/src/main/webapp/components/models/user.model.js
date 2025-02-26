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

angular.module('privatlakareApp').factory('UserModel',
    function($window, $timeout, $cookies, $injector) {
      'use strict';

      var data = {};

      function _reset() {
        data.name = null;
        data.status = null;
        data.personnummer = null;
        data.authenticationScheme = null;
        data.fakeSchemeId = 'urn:inera:privatlakarportal:eleg:fake';
        data.loggedIn = false;
        data.nameFromPuService = false;
        data.nameUpdated = false;
        return data;
      }

      
      return {

        reset: _reset,
        init: function() {
          return _reset();
        },

        set: function(user) {
          data.name = user.name;
          data.personnummer = user.personalIdentityNumber;

          if (typeof data.personnummer === 'string') {
            var dashIndex = data.personnummer.length - 4;
            if (data.personnummer.charAt(dashIndex - 1) !== '-') {
              data.personnummer = data.personnummer.slice(0, dashIndex) + '-' + data.personnummer.slice(dashIndex);
            }
          }

          data.status = user.status;
          data.authenticationScheme = user.authenticationScheme;
          data.loggedIn = true;
          data.nameFromPuService = user.nameFromPuService;
          data.nameUpdated = user.nameUpdated;
        },
        get: function() {
          return data;
        },
        getStatusText: function() {
          var statusText;
          switch (data.status) {
          case 'AUTHORIZED':
          case 'NOT_AUTHORIZED':
          case 'WAITING_FOR_HOSP':
            statusText = 'Privatläkare';
            break;
          default:
            statusText = 'Ej registrerad ännu';
          }
          return statusText;
        },
        hasApplicationPermission: function() {
          return data.status === 'AUTHORIZED';
        },
        isRegistered: function() {
          return data.status === 'NOT_AUTHORIZED' || data.status === 'AUTHORIZED' || data.status === 'WAITING_FOR_HOSP';
        },
        logout: function() {
          if (data.authenticationScheme === data.fakeSchemeId) {
            var http = $injector.get('$http');
            http({
              url: '/api/testability/logout',
              method: 'POST'
            })
            .then(function() {
              $window.location.href = '/welcome.html';
            });
          } else {
            var form = angular.element('<form></form>');
            var input = angular.element('<input type="hidden" name="_csrf" />');
            form.attr('method', 'POST');
            form.attr('action', '/logout');
            var csrfToken = $cookies.get('XSRF-TOKEN') || '';
            input.val(csrfToken);
            form.append(input);
            angular.element(document.body).append(form);
            form[0].submit();
          }
        }
      };
    }
);
