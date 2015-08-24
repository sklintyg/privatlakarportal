package se.inera.privatlakarportal.page

class WelcomePage extends AbstractPage {
    static url = "/welcome.html"
    static at = { $("#loginForm").isDisplayed() }

    static content = {
        userSelect { $("#jsonSelect") }
        loginBtn { $("#loginBtn") }
    }

    def loginAs(String id) {
        userSelect = $("#${id}").value();
        loginBtn.click()
        waitFor {
            doneLoading()
        }
    }
}
