describe('Personuppgifter', function() {

    it('Stoppa registrering om personuppgifter ej kan hämtas', function() {
        cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);

        cy.login('3');

        cy.url().should('contain', '/error')
    })

    it('Uppdatera namn om nya personuppgifter finns', function() {
        cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);

        cy.skapaPrivatlakare().its('status').should('eq', 200);

        cy.bytNamnPrivatLakare('199008252398', 'FEL');

        cy.login('0');

        cy.get('#nyttNamnInformation').should('be.visible');
        cy.taBortPrivatlakare('199008252398').its('status').should('eq', 200);
    })
});
