package se.inera.privatlakarportal.page

class RegisterStep2Page extends RegisterPage {
    static url = "/#/registrera/steg2"
    static at = { $("#step2").isDisplayed() }

    static content = {
        continueBtn(to: RegisterStep3Page, toWait:true) { $("#continueBtn")}
        backBtn(to: RegisterStep1Page, toWait:true) { $("#backBtn")}
        abortBtn(to: RegisterStep1AbortPage, toWait: true) { $("#abortBtn")}
    }

    public void fortsätt() {
        continueBtn.click();
    }

    public void tillbaka() {
        backBtn.click();
    }

    public void avbryt() {
        abortBtn.click();
    }
}
