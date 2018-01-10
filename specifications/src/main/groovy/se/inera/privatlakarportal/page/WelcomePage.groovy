/*
 * Copyright (C) 2018 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.page

import se.inera.intyg.privatlakarportal.spec.Browser

class WelcomePage extends AbstractPage {
    static url = "/welcome.html"
    static at = { $("#loginForm").isDisplayed() && doneLoading() }

    static content = {
        userSelect { $("#jsonSelect") }
        loginBtn { $("#loginBtn") }
    }

    def loginAs(String id) {
        userSelect = $("#${id}").value();
        loginBtn.click()
        // The login page is running a separate angular app, here we need to wait for the "real" angular app to start.
        Browser.drive {
            waitFor {
                js.doneLoading
            }
        }
    }
}
