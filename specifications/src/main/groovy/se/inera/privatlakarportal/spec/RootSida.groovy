package se.inera.privatlakarportal.spec

import se.inera.privatlakarportal.spec.Browser
import se.inera.privatlakarportal.page.RootPage

class RootSida {
    void execute() {
        Browser.drive {
            to RootPage
            assert at(RootPage)
        }
    }
    
    boolean test() {
        return true;
    }
    
}
