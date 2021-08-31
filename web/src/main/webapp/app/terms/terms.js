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

  function openExternalAppTerms(AppTermsModalModel, TermsService) {
    var modalModel = AppTermsModalModel.init();
    modalModel.options.controller = 'WebcertTermsCtrl';
    modalModel.options.titleId = 'label.modal.title.webcertvillkor';
    TermsService.openTerms(modalModel);
  }

  function openPortalTerms(AppTermsModalModel, TermsService) {
    var modalModel = AppTermsModalModel.init();
    modalModel.options.controller = 'PortalTermsCtrl';
    modalModel.options.titleId = 'label.modal.title.portalvillkor';
    TermsService.openTerms(modalModel);
  }

  function closeTerms(AppTermsModalModel) {
    var modal = AppTermsModalModel.get().modalInstance;
    if (modal) {
      modal.dismiss('cancel');
    }
  }

  $stateProvider
  .state('app.start.terms', {
    url: '/terms',
    onEnter: openExternalAppTerms,
    onExit: closeTerms,
    params: {
      terms: null,
      termsData: null
    },
    data: {
      rule: function(fromState) {
        if (fromState.name !== 'app.start') {
          return {
            to: 'app.start'
          };
        }
      }
    }
  }).state('app.register.step3.terms', {
    url: '/terms',
    onEnter: openPortalTerms,
    onExit: closeTerms,
    params: {
      terms: null,
      termsData: null
    },
    data: {
      rule: function(fromState) {
        if (fromState.name !== 'app.register.step3') {
          return {
            to: 'app.register.step3'
          };
        }
      }
    }
  }).state('app.minsida.terms', {
    url: '/terms',
    onEnter: openExternalAppTerms,
    onExit: closeTerms,
    params: {
      terms: 'webcert',
      termsData: null
    },
    data: {
      rule: function(fromState) {
        if (fromState.name !== 'app.minsida') {
          return {
            to: 'app.minsida'
          };
        }
      }
    }
  });
});
