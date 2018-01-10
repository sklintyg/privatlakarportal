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

class MinSidaPage extends AbstractPage {
    static url = "/#/minsida"
    static at = { doneLoading() && $("#minsida").isDisplayed() }

    static content = {
        sparaBtn { $("#saveBtn")}

        uppdateratNamnInformationstext(required: false) { $("#nyttNamnInformation") }

        personnummer { $("#personnummer") }
        namn { $("#namn") }
        befattning { $("#befattning") }
        verksamhetensnamn { $("#verksamhetensnamn") }
        agarform { $("#agarform") }
        vardform { $("#vardform") }
        verksamhetstyp { $("#verksamhetstyp") }
        arbetsplatskod { $("#arbetsplatskod") }

        telefonnummer { $("#telefonnummer") }
        epost { $("#epost") }
        epost2 { $("#epost2") }
        adress { $("#adress") }
        postnummer { $("#postnummer") }
        postort { $("#postort") }
        kommun { $("#valdKommun") }
        lan { $("#lan") }

        legitimeradYrkesgrupp { $("#legitimeradYrkesgrupp") }
        specialitet { $("#specialitet") }
        forskrivarkod { $("#forskrivarkod") }
    }

    public void spara() {
        sparaBtn.click();

        Browser.drive {
            waitFor {
                at WelcomePage
            }
        }
    }

    public void angeBefattning(value) {
        befattning = value;
    }

    public void angeVerksamhetensnamn(value) {
        verksamhetensnamn = value;
    }

    public void angeVardform(value) {
        vardform = value;
    }

    public void angeVerksamhetstyp(value) {
        verksamhetstyp = value;
    }

    public void angeArbetsplatskod(value) {
        arbetsplatskod = value;
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

    public String hamtaPersonnummer() { return personnummer.text() }
    public String hamtaNamn() { return namn.text() }
    public String hamtaBefattning() { return befattning.find('option').find{ it.value() == befattning.value()}.text()  }
    public String hamtaVerksamhetensnamn() { return verksamhetensnamn.value() }
    public String hamtaAgarform() { return agarform.text() }
    public String hamtaVardform() { return vardform.find('option').find{ it.value() == vardform.value()}.text() }
    public String hamtaVerksamhetstyp() { return verksamhetstyp.find('option').find{ it.value() == verksamhetstyp.value()}.text() }
    public String hamtaArbetsplatskod() { return arbetsplatskod.value() }

    public String hamtaTelefonnummer() { return telefonnummer.value() }
    public String hamtaEpost() { return epost.value() }
    public String hamtaAdress() { return adress.value() }
    public String hamtaPostnummer() { return postnummer.value() }
    public String hamtaPostort() { return postort.text() }
    public String hamtaKommun() { return kommun.text() }
    public String hamtaLan() { return lan.text() }

    public String hamtaLegitimeradYrkesgrupp() { return legitimeradYrkesgrupp.text() }
    public String hamtaSpecialitet() { return specialitet.text() }
    public String hamtaForskrivarkod() { return forskrivarkod.text() }

    public boolean uppdateratNamnInformationstextenVisas() {
        return uppdateratNamnInformationstext.displayed
    }

}
