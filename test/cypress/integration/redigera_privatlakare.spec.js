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

describe('Redigera Privatläkare', function() {

  it('redigera', function() {
    cy.server();
    cy.route({
      method: 'GET',
      url: '**api/registration/omrade/**'
    }).as('postnummer-api');

    //Rensa
    cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);

    //Sapa privatläkare via REST
    cy.skapaPrivatlakare().its('status').should('eq', 200);

    //Logga in som privatläkare
    cy.login('0');

    //Gå till min sida
    cy.get('#minsida').should('be.visible');

    //Fyll i registrering
    cy.wait('@postnummer-api');
    cy.get('#befattning').select('202010');
    cy.get('#verksamhetensnamn').clear().type('Fitnesse verksamhet');
    cy.get('#vardform').select('03');
    cy.get('#verksamhetstyp').select('15');
    cy.get('#arbetsplatskod').clear().type('770088');
    cy.get('#telefonnummer').clear().type('987654321');
    cy.get('#epost').clear().type('test@example.com');
    cy.get('#epost2').clear().type('test@example.com');
    cy.get('#adress').clear().type('Gatuadressen');
    cy.get('#postnummer').clear().type('13100');
    cy.wait('@postnummer-api');
    cy.wait(100);
    cy.get('#saveBtn').click();

    cy.get('#loginForm').should('be.visible');
  });

  it('verifiera', function() {
    //Logga in som privatläkare
    cy.login('0');

    //Gå till min sida
    cy.get('#minsida').should('be.visible');

    //Verifiera registrering
    cy.get('#personnummer').should('have.text', '19900825-2398');
    cy.get('#namn').should('have.text', 'Oskar II Johansson');
    cy.get('#befattning').should('have.value', '202010');
    cy.get('#verksamhetensnamn').should('have.value', 'Fitnesse verksamhet');
    cy.get('#agarform').should('have.text', 'Privat');
    cy.get('#vardform').should('have.value', '03');
    cy.get('#verksamhetstyp').should('have.value', '15');
    cy.get('#arbetsplatskod').should('have.value', '770088');

    cy.get('#telefonnummer').should('have.value', '987654321');
    cy.get('#epost').should('have.value', 'test@example.com');
    cy.get('#adress').should('have.value', 'Gatuadressen');
    cy.get('#postnummer').should('have.value', '13100');
    cy.get('#postort').should('contain', 'NACKA');
    cy.get('#valdKommun').should('contain', 'NACKA');
    cy.get('#lan').should('contain', 'STOCKHOLM');
    cy.get('#legitimeradYrkesgrupp').should('have.text', 'Läkare');
    cy.get('#specialitet').should('have.text', 'specialityName1, specialityName2');
    cy.get('#forskrivarkod').should('have.text', '1234567');
  });

  after(function() {
    cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);
  });

});
