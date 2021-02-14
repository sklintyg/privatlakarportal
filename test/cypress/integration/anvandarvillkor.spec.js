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

describe('Användarvillkor', function() {

  it('Användarvillkor ska visas i modal', function() {
    cy.server();
    cy.route({
      method: 'GET',
      url: '**/api/terms/webcert**'
    }).as('api-terms');
    cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);

    cy.login('0');

    cy.get('#registerBtn').should('be.visible');
    cy.get('.modal-dialog').should('not.be.visible');

    cy.get('#termsLink').click();
    cy.wait('@api-terms');
    cy.get('.modal-dialog').should('be.visible');
    cy.get('#termsModal').should('not.be.empty');

    cy.get('#dismissBtn').click();
    cy.wait(500);
    cy.get('.modal-dialog').should('not.exist');
  });

  after(function() {
    cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);
  });
});
