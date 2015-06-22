package se.inera.privatlakarportal.page

class StartPage extends AbstractPage {
    static url = "http://localhost:8090"
    static at = { registerBtn.isDisplayed() && !$("#termsModal").isDisplayed() }

    static content = {
        registerBtn(to: RegisterStep1Page, toWait:true) { $("#registerBtn")}
        termsLink(to: TermsPage, toWait: true) { $("#termsLink")}
    }

    public void startaRegistrering() {
        registerBtn.click();
    }

    public void visaAnvändarvillkoren() {
        termsLink.click();
    }
}
