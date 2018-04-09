describe('Rensa Privatläkare', function() {

    it('rensa', function() {
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

        cy.wait(1000);

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
        cy.wait(1000);
        cy.korHospUppdatering().its('status').should('eq', 200);

        //Verifiera att det inte går att logga in med tolvan.
        cy.loggaInGenomWebcert('191212121212').its('body').should('have.property', 'resultCode').should('eq', 'ERROR');

        //Tolvan ska inte bli borttagen efter en hospuppdatering eftersom han tidigare loggat in.
        cy.finnsPrivatlakare('191212121212').its('body').should('have.property', 'personId');

        //Rensa
        cy.taBortPrivatlakare('195206172339').its('status').should('eq', 200);
        cy.taBortPrivatlakare('191212121212').its('status').should('eq', 200);

        //Återställ tolvans hosp
        cy.laggTillHospInformation('191212121212', true);

    });

});
