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
describe('Controller: BootCtrl', function() {
    'use strict';

    var user = {};

    // load the controller's module
    beforeEach(angular.mock.module('htmlTemplates'));
    beforeEach(angular.mock.module('privatlakareApp'));

    var $rootScope , $controller, $state, scope, UserModel, mockResponse;

    // Initialize the controller and a mock scope
    beforeEach(inject(function(_$controller_, _$rootScope_, _UserModel_, _$state_, _mockResponse_) {
        $rootScope = _$rootScope_;
        $controller = _$controller_;
        scope = $rootScope.$new();
        UserModel = _UserModel_;
        $state = _$state_;
        mockResponse = _mockResponse_;
    }));

    it('should be redirected to start page if not registered yet', function() {
        spyOn($state, 'go').and.stub();

        user = angular.copy(mockResponse.userModel);
        user.status = 'NOT_STARTED';
        user.nameFromPuService = true;
        UserModel.set(user);

        $controller('BootCtrl', { $scope: scope });

        expect($state.go).toHaveBeenCalledWith('app.start');
    });

    it('should be redirected to error page if not registered yet and unable to get name from pu service', function() {
        spyOn($state, 'go').and.stub();

        user = angular.copy(mockResponse.userModel);
        user.status = 'NOT_STARTED';
        user.nameFromPuService = false;
        UserModel.set(user);

        $controller('BootCtrl', { $scope: scope });

        expect($state.go).toHaveBeenCalledWith('app.error',
            { errorMessage: 'Ett tekniskt fel har tyvärr uppstått och det går inte att hämta dina namnuppgifter ' +
                'från folkbokföringsregistret för tillfället. Du kan därför inte skapa ett konto för Webcert just nu. ' +
                'Prova igen om en stund.' });
    });

    it('should be redirected to minsida page if registered but havent received läkarlegimitation yet', function() {
        spyOn($state, 'go').and.stub();

        user = angular.copy(mockResponse.userModel);
        user.status = 'WAITING_FOR_HOSP';
        UserModel.set(user);

        $controller('BootCtrl', { $scope: scope });

        expect($state.go).toHaveBeenCalledWith('app.minsida');
    });

    it('should be redirected to minsida page if registered and got läkarlegitimation', function() {
        spyOn($state, 'go').and.stub();

        user = angular.copy(mockResponse.userModel);
        user.status = 'AUTHORIZED';
        UserModel.set(user);

        $controller('BootCtrl', { $scope: scope });

        expect($state.go).toHaveBeenCalledWith('app.minsida');
    });

});
