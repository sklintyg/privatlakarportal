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

angular.module('privatlakareApp')
.config(function($stateProvider) {
  'use strict';

  var abortUrl = '/avbryt';

  function showAbortDialog($state, dialogService) {
    dialogService.open({
      templateUrl: '/app/register/abort/abort.html',
      controller: 'RegisterAbortCtrl'
    }).result.finally(function() { //jshint ignore:line
      if ($state.current.url === abortUrl) {
        $state.go('^');
      }
    });
  }

  function closeAbortDialog(dialogService) {
    dialogService.close();
  }

  $stateProvider
  .state('app.register.step1.abort', {
    url: abortUrl,
    onEnter: showAbortDialog,
    onExit: closeAbortDialog
  })
  .state('app.register.step2.abort', {
    url: abortUrl,
    onEnter: showAbortDialog,
    onExit: closeAbortDialog
  })
  .state('app.register.step3.abort', {
    url: abortUrl,
    onEnter: showAbortDialog,
    onExit: closeAbortDialog
  });
});