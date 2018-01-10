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

class RegisterStep2Page extends RegisterPage {
    static url = "/#/registrera/steg2"
    static at = { doneLoading() && $("#step2").isDisplayed() }

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

    public void angeTelefonnummer(value) {
        telefonnummer = value;
    }

    public void angeEpost(value) {
        epost = value;
    }

    public void angeEpost2(value) {
        epost2 = value;
    }

    public void angeGatuadress(value) {
        adress = value;
    }

    public void angePostnummer(value) {
        postnummer = value;
    }

}
