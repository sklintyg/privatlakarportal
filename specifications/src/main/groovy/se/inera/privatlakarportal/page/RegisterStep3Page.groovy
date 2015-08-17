package se.inera.privatlakarportal.page

class RegisterStep3Page extends RegisterPage {
    static url = "/#/registrera/steg3"
    static at = { $("#step3").isDisplayed() }

    static content = {
        registerBtn { $("#registerBtn")}
        backBtn(to: RegisterStep2Page, toWait:true) { $("#backBtn")}
        abortBtn(to: RegisterStep1AbortPage, toWait: true) { $("#abortBtn")}

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
        adress { $("#adress") }
        postnummer { $("#postnummer") }
        postort { $("#postort") }
        kommun { $("#kommun") }

        legitimeradYrkesgrupp { $("#legitimeradYrkesgrupp") }
        specialitet { $("#specialitet") }
        forskrivarkod { $("#forskrivarkod") }
    }

    public void registreraMig() {
        registerBtn.click()
    }

    public void tillbaka() {
        backBtn.click()
    }

    public void avbryt() {
        abortBtn.click()
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

    public String hamtaLegitimeradYrkesgrupp() { return legitimeradYrkesgrupp.text() }
    public String hamtaSpecialitet() { return specialitet.text() }
    public String hamtaForskrivarkod() { return forskrivarkod.text() }
}
