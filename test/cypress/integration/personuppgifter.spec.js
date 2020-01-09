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

describe('Personuppgifter', function() {

  it('Stoppa registrering om personuppgifter ej kan hämtas', function() {
    cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);

    cy.login('3');

    cy.url().should('contain', '/error')
  });

  it('Uppdatera namn om nya personuppgifter finns', function() {
    cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);

    cy.skapaPrivatlakare().its('status').should('eq', 200);

    cy.bytNamnPrivatLakare('199008252398', 'FEL');

    cy.login('0');

    cy.get('#nyttNamnInformation').should('be.visible');
  });

  after(function() {
    cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);
  });
});
