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

describe('Registrera Privatläkare', function() {

  it('Fyll i registrering', function() {

    cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);
    cy.rensaMailStubbe().its('status').should('eq', 200);

    // Logga in
    cy.login('0');
    cy.get('#cookie-usage-consent-btn').click();

    // Stara registrering
    cy.get('#registerBtn').click();

    // Fyll i steg 1
    cy.get('#step1').should('be.visible');
    cy.get('#befattning').select('Specialistläkare');
    cy.get('#verksamhetensnamn').clear().type('Fitnesse verksamhet');
    cy.get('#vardform').select('Hemsjukvård');
    cy.get('#verksamhetstyp').select('Primärvårdsverksamhet');
    cy.get('#arbetsplatskod').clear().type('770088');
    cy.get('#continueBtn').click();

    // Fyll i steg 2
    cy.get('#step2').should('be.visible');
    cy.get('#telefonnummer').clear().type('987654321');
    cy.get('#epost').clear().type('test@example.com');
    cy.get('#epost2').clear().type('test@example.com');
    cy.get('#adress').clear().type('gatuadress');
    cy.get('#postnummer').clear().type('13100');
    cy.wait(300);
    cy.get('#continueBtn').click();

    // Verifiera steg 3
    cy.get('#step3').should('be.visible');
    cy.get('#personnummer').should('have.text', '19900825-2398');
    cy.get('#namn').should('have.text', 'Oskar II Johansson');
    cy.get('#befattning').should('have.text', 'Specialistläkare');
    cy.get('#verksamhetensnamn').should('have.text', 'Fitnesse verksamhet');
    cy.get('#agarform').should('have.text', 'Privat');
    cy.get('#vardform').should('have.text', 'Hemsjukvård');
    cy.get('#verksamhetstyp').should('have.text', 'Primärvårdsverksamhet');
    cy.get('#arbetsplatskod').should('have.text', '770088');

    cy.get('#telefonnummer').should('have.text', '987654321');
    cy.get('#epost').should('have.text', 'test@example.com');
    cy.get('#adress').should('have.text', 'gatuadress');
    cy.get('#postnummer').should('have.text', '13100');
    cy.get('#postort').should('have.text', 'NACKA');
    cy.get('#kommun').should('have.text', 'NACKA');
    cy.get('#lan').should('have.text', 'STOCKHOLM');
    cy.get('#legitimeradYrkesgrupp').should('have.text', 'Läkare');
    cy.get('#specialitet').should('have.text', 'specialityName1, specialityName2');
    cy.get('#forskrivarkod').should('have.text', '1234567');

    cy.get('#hospLoad').should('not.be.visible');
    cy.get('#hospUpdate').should('not.be.visible');
    cy.get('#hospComplete').should('be.visible');

    // Godkänn användarvillkoren
    cy.get('#registerBtn').should('be.disabled');
    cy.get('#godkannvillkor').check();
    cy.get('#registerBtn').should('not.be.disabled');
    cy.get('#registerBtn').click();

    // Klar sidan visas
    cy.get('#complete').should('be.visible');
    cy.wait(300);

    cy.hamtaMailFranStubbe('199008252398').should('eq', 'Registration klar');
  });

  after(function() {
    cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);
    cy.rensaMailStubbe().its('status').should('eq', 200);
  });

});
