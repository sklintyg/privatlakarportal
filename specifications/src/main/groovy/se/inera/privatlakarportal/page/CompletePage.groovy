package se.inera.intyg.privatlakarportal.page

class CompletePage extends AbstractPage {
    static at = { doneLoading() && $("#complete").isDisplayed() }

    static content = {
        goToWebcertBtn { $("#goToWebcertBtn")}
    }

    public void goToWebcert() {
        goToWebcertBtn.click();
    }
}
