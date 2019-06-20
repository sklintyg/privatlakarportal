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
