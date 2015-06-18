package se.inera.privatlakarportal.page

class TermsPage extends StartPage {
    static url = "http://localhost:8090"
    static at = { title == "Privatläkarportalen" }

    static content = {
        registerBtn(to: TermsPage, toWait:true) { $("#registerBtn")}
        termsLink(to: TermsPage, toWait: true) { $("#termsLink")}
    }
}
