/*
 * Copyright (C) 2019 Inera AB (http://www.inera.se)
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

class StartPage extends AbstractPage {
    static url = "http://localhost:8090"
    static at = { doneLoading() && registerBtn.isDisplayed() && !termsModal.isDisplayed() }

    static content = {
        termsModal(required:false) { $("#termsModal") }
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
