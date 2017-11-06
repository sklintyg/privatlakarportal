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
    cy.visit(Cypress.env('ppUrl') + '/welcome.html');
    cy.get('#jsonSelect').select(loginId);
    cy.get('#loginBtn').click();
});

function taBortPrivatlakare(route, id) {
    return cy.request('GET', Cypress.env('ppUrl') + '/api/test/registration/' + id, {'headers':{'Cookie':'ROUTEID='+route}}).then((resp) => {
        if (resp.body) {
            return cy.request('DELETE', Cypress.env('ppUrl') + '/api/test/registration/remove/'+id, {'headers': {'Cookie': 'ROUTEID=' + route}});
        } else {
            return resp;
        }
    });
}

Cypress.Commands.add("taBortPrivatlakare", (id) => {
    Cypress.Promise.join(taBortPrivatlakare('.1', id), taBortPrivatlakare('.2', id)).then((res1, res2) => {
        cy.log('taBortPrivatlakare',res1.body,res1.status, res2.body,res2.status);
        if (res1.status != 200) {
            return res1;
        }
        return res2;
    });
});

Cypress.Commands.add("rensaMailStubbe", () => {
    cy.request('DELETE', Cypress.env('ppUrl') + '/api/stub/mails/clear', {'headers':{'Cookie':'ROUTEID=.1'}});
    cy.request('DELETE', Cypress.env('ppUrl') + '/api/stub/mails/clear', {'headers':{'Cookie':'ROUTEID=.2'}});
});

Cypress.Commands.add("hamtaMailFranStubbe", (mailId) => {
    cy.request('GET', Cypress.env('ppUrl') + '/api/stub/mails', {'headers':{'Cookie':'ROUTEID=.1'}}).then((resp) => {
        if (resp.body[mailId]) {
            return resp.body[mailId];
        }
        return cy.request('GET', Cypress.env('ppUrl') + + 'api/stub/mails', {'headers':{'Cookie':'ROUTEID=.2'}}.then((resp2) => {
            if (resp2.body[mailId]) {
                return resp2.body[mailId];
            }
        }));
    });
});
