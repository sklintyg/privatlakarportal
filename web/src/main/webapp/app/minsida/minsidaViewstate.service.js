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

angular.module('privatlakareApp').service('MinsidaViewState',
    function(messageService) {
      'use strict';

      this.reset = function() {

        this.errorMessage = {
          load: null,
          noPermission: false,
          save: null
        };

        this.infoMessage = {
          save: null
        };

        this.loading = {
          save: false
        };

        // UndoModel holds backup of loaded model in case user wants to undo changes
        this.undoModel = {};

        return this;
      };

      this.setUndoModel = function(model) {
        this.undoModel = angular.copy(model);
      };

      this.resetModelFromUndoModel = function(RegisterModel, form) {
        RegisterModel.set(this.undoModel);
        this.infoMessage.save = null;
        form.$setPristine();
        return RegisterModel.get();
      };

      this.changesRequireLogout = function() {
        return this.infoMessage.save !== null;
      };

      this.checkFieldsRequiringLogout = function(form) {
        // No need to do anything if form isn't dirty
        if (form.$dirty) {
          // These fields require logout for intyg to be updated correctly in webcert
          this.infoMessage.save = null;
          var logoutFields = ['verksamhetensnamn', 'telefonnummer', 'adress', 'postnummer'];
          angular.forEach(logoutFields, function(fieldName) {
            var field = form[fieldName];
            if (typeof field === 'undefined') {
              throw 'field ' + fieldName + ' does not exist';
            } else if (field && field.$dirty) {
              this.infoMessage.save = messageService.getProperty('label.summary.logoutinfo');
            }
          }, this);
        }
      };

      this.reset();
    }
);
