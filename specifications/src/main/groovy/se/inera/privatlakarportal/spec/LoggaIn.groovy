package se.inera.intyg.privatlakarportal.spec

import se.inera.intyg.privatlakarportal.page.WelcomePage

class LoggaIn {

    def loggaInSom(String id) {
        Browser.drive {
            to WelcomePage
            page.loginAs(id)
        }
    }
}
