package se.inera.privatlakarportal.page

import groovy.lang.MetaClass

abstract class RootPage extends AbstractPage {
    static url = "http://localhost:9090"
    static at = { title == "Privatläkarportalen" }
}
