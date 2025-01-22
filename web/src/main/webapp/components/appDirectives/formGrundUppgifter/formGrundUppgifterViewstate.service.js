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

angular.module('privatlakareApp').service('FormGrundUppgifterViewState',
    function(APP_CONFIG) {
      'use strict';

      this.reset = function() {
        function processListOptions(data) {
          var list = [];
          angular.forEach(data, function(value, key) {
            this.push({
              'id': key,
              'label': value
            });
          }, list);
          return list;
        }

        this.befattningList = processListOptions(APP_CONFIG.befattningar);
        this.vardformList = processListOptions(APP_CONFIG.vardformer);
        this.verksamhetstypList = processListOptions(APP_CONFIG.verksamhetstyper);
        return this;
      };

      this.reset();
    }
);
