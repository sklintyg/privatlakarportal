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

describe('Registrera Privatläkare utan hosp', function() {

  it('Fyll i registrering', function() {

    cy.taBortPrivatlakare('195206172339').its('status').should('eq', 200);
    cy.rensaMailStubbe().its('status').should('eq', 200);

    // Logga in
    cy.login('4');
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
    cy.get('#personnummer').should('have.text', '19520617-2339');
    cy.get('#namn').should('have.text', 'Björn Anders Daniel Pärsson');
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
    cy.get('#legitimeradYrkesgrupp').should('have.text', '');
    cy.get('#specialitet').should('have.text', '');
    cy.get('#forskrivarkod').should('have.text', '');

    cy.get('#hospLoad').should('be.visible');

    // Visa användarvillkor
    cy.get('#termsLink').click();
    cy.get('.modal-dialog').should('be.visible');
    cy.get('#termsModal').should('not.be.empty');

    // Stäng användarvillkor
    cy.get('#dismissBtn').click();
    cy.wait(300);
    cy.get('.modal-dialog').should('not.be.visible');

    // Godkänn användarvillkoren
    cy.get('#registerBtn').should('be.disabled');
    cy.get('#godkannvillkor').check();
    cy.get('#registerBtn').should('not.be.disabled');
    cy.get('#registerBtn').click();

    // Klar sidan visas
    cy.get('#waiting').should('be.visible');
    cy.wait(300);

    cy.hamtaMailFranStubbe('195206172339').its('body').should('be.empty');
    cy.taBortPrivatlakare('195206172339').its('status').should('eq', 200);
  });

  after(function() {
    cy.taBortPrivatlakare('195206172339').its('status').should('eq', 200);
    cy.rensaMailStubbe().its('status').should('eq', 200);
  });

});
