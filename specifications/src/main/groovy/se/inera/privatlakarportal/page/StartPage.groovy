package se.inera.privatlakarportal.page

class StartPage extends AbstractPage {
    static url = "http://localhost:8090"
    static at = { registerBtn.isDisplayed() }

    static content = {
        registerBtn(to: RegisterStep1Page, toWait:true) { $("#registerBtn")}
        termsLink(to: TermsPage, toWait: true) { $("#termsLink")}
    }

    def startaRegistrering() {
        registerBtn.click();
    }

    def visaAnvändarvillkoren() {
        termsLink.click();
    }
}
