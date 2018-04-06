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

Cypress.Commands.add("login", (loginId) => {
    cy.visit('/welcome.html');
    cy.get('#jsonSelect').select(loginId);
    cy.get('#loginBtn').click();
    cy.window().should('have.property', 'disableAnimations').then((disableAnimations) => {
        disableAnimations();
    });
});

Cypress.Commands.add("skapaPrivatlakare", () => {
    cy.visit('/welcome.html');
    cy.get('#jsonSelect').select('0');
    cy.get('#loginBtn').click();
    return cy.request('GET', '/api/user/').then((resp) => {
        if (resp.body) {
            cy.fixture('specialistlakare.json').then((userJson) => {
                userJson.godkantMedgivandeVersion = 1;
                return cy.request({method: 'POST', url: '/api/registration/create', body:userJson});
            });
        } else {
            return resp;
        }
    });
});

Cypress.Commands.add("taBortPrivatlakare", (id) => {
    return cy.request('GET', '/api/test/registration/' + id).then((resp) => {
        if (resp.body) {
            return cy.request('DELETE', '/api/test/registration/remove/'+id);
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
    cy.request({method: 'POST', url: '/api/test/registration/setname/' + id, body:{namn}}).then((resp) => {
        cy.log('bytNamnPrivatLakare', resp);
    });
});
