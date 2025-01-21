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

angular.module('privatlakareApp')
.controller('BootLinkCtrl', function($scope, $timeout, $state,
    UserProxy, UserModel) {
  'use strict';
  $scope.loading = true;
  $scope.errorMessage = null;

  UserProxy.getUser().then(function(successData) {
    $scope.loading = false;
    $scope.errorMessage = null;
    UserModel.set(successData);

    if ($state.params.targetId === 'new') {
      $state.go('app.register.step1');
    } else {
      $scope.errorMessage = 'Kunde inte länka rätt. Kontrollera parametern:' + $state.params.targetId;
    }
  }, function() {
    $scope.loading = false;
    $scope.errorMessage = 'Kunde inte hämta användare. Har du loggat in?';
  });
});
