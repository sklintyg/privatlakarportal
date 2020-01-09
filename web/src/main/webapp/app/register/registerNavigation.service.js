/*
 * Copyright (C) 2020 Inera AB (http://www.inera.se)
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

angular.module('privatlakareApp').service('RegisterNavigationService',
    function(ObjectHelper) {
      'use strict';

      // general step navigation
      this.getStepFromState = function(state) {
        if (!ObjectHelper.isDefined(state.data)) {
          return null;
        }

        return state.data.step;
      };

      this.navigationAllowed = function(toState, fromState, formValid) {
        // Prevent user from navigating forwards if clicking the fishbone nav
        var toStep = this.getStepFromState(toState);
        var fromStep = this.getStepFromState(fromState);

        if (toStep === null) { // step number is N/A. allow navigation because it is outside the register flow.
          return true;
        } else {
          return !(toStep > fromStep + 1 || (toStep === fromStep + 1 && !formValid));
        }
      };
    }
);
