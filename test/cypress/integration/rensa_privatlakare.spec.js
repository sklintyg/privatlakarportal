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

describe('Rensa Privatläkare', function() {

  it.skip('Testa lägga till användare, modifiera registreringsdatum, ta bort hosp data', function() {
    //Rensa
    cy.taBortPrivatlakare('195206172339').its('status').should('eq', 200);
    cy.taBortPrivatlakare('191212121212').its('status').should('eq', 200);

    //Sapa privatläkare via REST
    cy.skapaPrivatlakare('Björn Anders Daniel', 'Pärsson', '195206172339').its('status').should('eq', 200);
    cy.skapaPrivatlakare('Tolvan', 'Privatläkarsson', '191212121212').its('status').should('eq', 200);

    //Trigga rensningsrutin
    cy.korHospUppdatering().its('status').should('eq', 200);

    //Kolla att privatläkare finns
    cy.finnsPrivatlakare('195206172339').its('status').should('eq', 200);
    cy.finnsPrivatlakare('191212121212').its('status').should('eq', 200);

    //Ändra registreringsdatum
    cy.sattRegistreringsdatumForPrivatlakare('195206172339', '1980-01-01').its('status').should('eq', 200);
    cy.sattRegistreringsdatumForPrivatlakare('191212121212', '1980-01-01').its('status').should('eq', 200);

    //Trigga rensningsrutin
    cy.korHospUppdatering().its('status').should('eq', 200);

    //Kolla att privatläkare finns
    //195206172339 är inte registrerad i hosp och borde försvinna.
    cy.finnsPrivatlakare('195206172339').its('body').should('eq', '');
    //191212121212 är registrerad i hosp och borde inte försvinna.
    cy.finnsPrivatlakare('191212121212').its('body').should('have.property', 'personId');

    //Verifiera att det går att logga in med Tolvan
    cy.loggaInGenomWebcert('191212121212').its('status').should('eq', 200);

    //Ta bort hosp information från tolvan.
    cy.taBortHospInformation('191212121212').its('status').should('eq', 200);

    //Vänta på att det slagit igenom
    cy.korHospUppdatering().its('status').should('eq', 200);

    //Verifiera att det inte går att logga in med tolvan.
    cy.loggaInGenomWebcert('191212121212').its('body').should('have.property', 'resultCode').should('eq', 'ERROR');

    //Tolvan ska inte bli borttagen efter en hospuppdatering eftersom han tidigare loggat in.
    cy.finnsPrivatlakare('191212121212').its('body').should('have.property', 'personId');
  });

  after(function() {
    //Rensa
    cy.taBortPrivatlakare('195206172339').its('status').should('eq', 200);
    cy.taBortPrivatlakare('191212121212').its('status').should('eq', 200);

    //Återställ tolvans hosp
    cy.laggTillHospInformation('191212121212', true);
  });

});
