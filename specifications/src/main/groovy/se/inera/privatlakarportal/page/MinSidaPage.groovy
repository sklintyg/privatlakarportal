package se.inera.privatlakarportal.page

class MinSidaPage extends AbstractPage {
    static url = "/#/minsida"
    static at = { $("#minsida").isDisplayed() }

    static content = {
        sparaBtn { $("#sparaBtn")}

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
        kommun { $("#kommun") }
        lan { $("#lan") }

        legitimeradYrkesgrupp { $("#legitimeradYrkesgrupp") }
        specialitet { $("#specialitet") }
        forskrivarkod { $("#forskrivarkod") }
    }

    public void spara() {
        sparaBtn.click();
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
    public String hamtaBefattning() { return befattning.text() }
    public String hamtaVerksamhetensnamn() { return verksamhetensnamn.text() }
    public String hamtaAgarform() { return agarform.text() }
    public String hamtaVardform() { return vardform.text() }
    public String hamtaVerksamhetstyp() { return verksamhetstyp.text() }
    public String hamtaArbetsplatskod() { return arbetsplatskod.text() }

    public String hamtaTelefonnummer() { return telefonnummer.text() }
    public String hamtaEpost() { return epost.text() }
    public String hamtaAdress() { return adress.text() }
    public String hamtaPostnummer() { return postnummer.text() }
    public String hamtaPostort() { return postort.text() }
    public String hamtaKommun() { return kommun.text() }
    public String hamtaLan() { return lan.text() }

    public String hamtaLegitimeradYrkesgrupp() { return legitimeradYrkesgrupp.text() }
    public String hamtaSpecialitet() { return specialitet.text() }
    public String hamtaForskrivarkod() { return forskrivarkod.text() }

}
