package se.inera.privatlakarportal.spec

import se.inera.privatlakarportal.spec.Browser
import se.inera.privatlakarportal.page.RootPage

class RootSida {

    boolean test

    void execute() {
        Browser.drive {
            to RootPage
            assert at(RootPage)
            test = true
        }
    }

    boolean test() {
        return test
    }

}
