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
describe('Service: minsidaViewstate', function() {
    'use strict';

    // load the controller's module
    beforeEach(module('privatlakareApp', function(/*$provide*/) {
    }));

    var MinsidaViewState;

    beforeEach(inject(function(_MinsidaViewState_) {
        MinsidaViewState = _MinsidaViewState_;
    }));

    describe('changesRequireLogout', function() {

        it('should check if changes have been made to fields requiring logout', function() {

            MinsidaViewState.infoMessage.save = 'Logout please text';
            expect(MinsidaViewState.changesRequireLogout()).toBe(true);

            MinsidaViewState.infoMessage.save = null;
            expect(MinsidaViewState.changesRequireLogout()).toBe(false);
        });
    });

    describe('checkFieldsRequiringLogout should tell user logout is required if changes have been made to fields requiring logout', function() {

        var registerForm;

        beforeEach(function() {
            registerForm = {
                '$dirty': false,
                'arbetsplatskod': {$dirty: false},
                'verksamhetensnamn' : {$dirty: false},
                'telefonnummer' : {$dirty: false},
                'adress' : {$dirty: false},
                'postnummer' : {$dirty: false}
            };
        });

        it('should update message if form is dirty and a required field has changed', function() {
            registerForm.$dirty = true;
            registerForm.verksamhetensnamn.$dirty = true;
            MinsidaViewState.checkFieldsRequiringLogout(registerForm);
            expect(MinsidaViewState.infoMessage.save).not.toBe(null);
        });

        it('should NOT update message if form is dirty and only a voluntary field has changed', function() {
            registerForm.$dirty = true;
            registerForm.arbetsplatskod.$dirty = true;
            MinsidaViewState.checkFieldsRequiringLogout(registerForm);
            expect(MinsidaViewState.infoMessage.save).toBe(null);
        });
    });
});
