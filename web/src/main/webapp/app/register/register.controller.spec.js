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
describe('Controller: RegisterCtrl', function() {
    'use strict';

    // load the controller's module
    beforeEach(module('privatlakareApp', function($provide) {
        $provide.value('APP_CONFIG', {});
    }));

    var scope, $rootScope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function($controller, _$rootScope_) {
        $rootScope = _$rootScope_;
        scope = $rootScope.$new();
        $controller('RegisterCtrl', {
            $scope: scope
        });
    }));

    it('should not step ahead if not allowed to change state', function() {
        $rootScope.$broadcast('$stateChangeStart', { data: {step: 1 }});
        expect(scope.step).toEqual(1);
        $rootScope.$broadcast('$stateChangeSuccess', { data: {step: 2 }});
        expect(scope.step).toEqual(2);
    });
});
