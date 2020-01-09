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

// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This is will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })

var WAIT_BEFORE_HOSP_UPDATE_MS = 1000;

Cypress.Commands.add("login", (loginId) => {
  cy.visit('/welcome.html');
  cy.wait(200);
  cy.get('#jsonSelect').select(loginId);
  cy.get('#loginBtn').click();
  cy.window().should('have.property', 'disableAnimations').then((disableAnimations) => {
    disableAnimations();
  });
});

function fakeLogin(firstName, lastName, personId) {
  if (!firstName) {
    firstName = "Oskar";
    lastName = "Johansson";
    personId = "199008252398";
  }
  var loginData = {
    "firstName": firstName, "lastName": lastName, "personId": personId
  };
  cy.request({method: 'POST', url: '/fake?userJsonDisplay=' + JSON.stringify(loginData)});
}

Cypress.Commands.add("skapaPrivatlakare", (firstName, lastName, personId) => {
  fakeLogin(firstName, lastName, personId);
  return cy.request('GET', '/api/user/').then((resp) => {
    if (resp.body) {
      return cy.fixture('specialistlakare.json').then((userJson) => {
        userJson.godkantMedgivandeVersion = 1;
        return cy.request({method: 'POST', url: '/api/registration/create', body: userJson});
      });
    } else {
      return resp;
    }
  });
});

Cypress.Commands.add("taBortPrivatlakare", (id) => {
  return cy.request('GET', '/api/test/registration/' + id).then((resp) => {
    if (resp.body) {
      return cy.request('DELETE', '/api/test/registration/remove/' + id);
    } else {
      return resp;
    }
  });
});

Cypress.Commands.add("rensaMailStubbe", () => {
  cy.request('DELETE', '/api/stub/mails/clear');
});

Cypress.Commands.add("hamtaMailFranStubbe", (mailId) => {
  cy.request('GET', '/api/stub/mails').then((resp) => {
    if (resp.body[mailId]) {
      return resp.body[mailId];
    }
  });
});

Cypress.Commands.add("bytNamnPrivatLakare", (id, namn) => {
  cy.request({method: 'POST', url: '/api/test/registration/setname/' + id, body: {namn}});
});

Cypress.Commands.add("sattRegistreringsdatumForPrivatlakare", (id, date) => {
  return cy.request({method: 'POST', url: '/api/test/registration/setregistrationdate/' + id, body: date});
});

Cypress.Commands.add("korHospUppdatering", () => {
  cy.wait(WAIT_BEFORE_HOSP_UPDATE_MS);
  return cy.request({method: 'POST', url: '/api/test/hosp/update'});
});

Cypress.Commands.add("finnsPrivatlakare", (personId) => {
  return cy.request({method: 'GET', url: '/api/test/registration/' + personId});
});

Cypress.Commands.add("loggaInGenomWebcert", (personId) => {
  return cy.request({method: 'POST', url: '/api/test/webcert/validatePrivatePractitioner/' + personId});
});

Cypress.Commands.add("taBortHospInformation", (personId) => {
  return cy.request({method: 'DELETE', url: '/api/test/hosp/remove/' + personId});
});

Cypress.Commands.add("laggTillHospInformation", (personId, lakarbehorighet) => {
  var hospInfo = {
    'personalIdentityNumber': personId,
    'personalPrescriptionCode': '1234567',
    'educationCodes': [],
    'restrictions': [],
    'restrictionCodes': [],
    'specialityCodes': lakarbehorighet ? ['32', '74'] : [],
    'specialityNames': lakarbehorighet ? ['Klinisk fysiologi', 'Nukleärmedicin'] : [],
    'titleCodes': lakarbehorighet ? ['LK'] : [],
    'hsaTitles': lakarbehorighet ? ['Läkare'] : []
  };
  cy.request({method: 'POST', url: '/api/test/hosp/add', body: hospInfo});
});
