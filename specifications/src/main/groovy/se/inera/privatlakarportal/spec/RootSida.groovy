package se.inera.privatlakarportal.spec

import se.inera.privatlakarportal.page.StartPage

class RootSida {

    boolean test

    void execute() {
        Browser.drive {
            to StartPage
            assert at(StartPage)
            test = true
        }
    }

    boolean test() {
        return test
    }

}
