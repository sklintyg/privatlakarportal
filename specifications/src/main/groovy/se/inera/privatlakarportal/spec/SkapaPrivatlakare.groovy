package se.inera.privatlakarportal.spec

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.core.io.ClassPathResource
import se.inera.privatlakarportal.spec.util.RestClientFixture

import static groovyx.net.http.ContentType.JSON



public class SkapaPrivatlakare extends RestClientFixture {

    def test = '{ "registration": { "befattning": "201013", "verksamhetensNamn": "Kliniken", "agarForm": "Privat", "vardform": "03", "verksamhetstyp": "12", "arbetsplatskod": "0123456", "telefonnummer": "070123456", "epost": "a@a.a", "adress": "Gatan", "postnummer": "13100", "postort": "NACKA", "kommun": "NACKA", "lan": "STOCKHOLM" }}'

    String befattning
	String verksamhetensNamn
    String agarForm
	String vardform
	String verksamhetstyp
    String arbetsplatskod
    String telefonnummer
    String epost
	String adress
    String postnummer
    String postort
    String kommun
    String lan

    String mall = "default"
	String responseStatus;
    def privatlakare

	public String respons() {
		return responseStatus;
	}

	public void reset() {
        // reset defaults
        mall = "default"
        befattning = null
        verksamhetensNamn = null
        agarForm = null
        vardform = null
        verksamhetstyp = null
        arbetsplatskod = null
        telefonnummer = null
        epost = null
        adress = null
        postnummer = null
        postort = null
        kommun = null
        lan = null

        String responseStatus;
    }
	
    public void execute() {
        def restClient = createRestClient()
        def resp = restClient.post(
            path: 'registration/create',
            body: createPayload(),
            requestContentType: JSON)
        responseStatus = resp.status
    }

    protected createPayload() {
        test
        //'"' + overrideDocumentFromFixture() + '"'
    }

    protected overrideDocumentFromFixture() {
        try {
            privatlakare = new JsonSlurper().parse(new InputStreamReader(new ClassPathResource("${mall}_template.json").getInputStream()))
        } catch (IOException e) {
            // if template for specific type cannot be loaded, use generic template
            privatlakare = new JsonSlurper().parse(new InputStreamReader(new ClassPathResource("generic_template.json").getInputStream()))
        }

        if(befattning) privatlakare.registration.befattning = befattning;
        if(verksamhetensNamn) privatlakare.registration.verksamhetensNamn = verksamhetensNamn;
        if(agarForm) privatlakare.registration.agarForm = agarForm;
        if(vardform) privatlakare.registration.vardform = vardform;
        if(verksamhetstyp) privatlakare.registration.verksamhetstyp = verksamhetstyp;
        if(arbetsplatskod) privatlakare.registration.arbetsplatskod = arbetsplatskod;
        if(telefonnummer) privatlakare.registration.telefonnummer = telefonnummer;
        if(epost) privatlakare.registration.epost = epost;
        if(adress) privatlakare.registration.adress = adress;
        if(postnummer) privatlakare.registration.postnummer = postnummer;
        if(postort) privatlakare.registration.postort = postort;
        if(kommun) privatlakare.registration.kommun = kommun;
        if(lan) privatlakare.registration.lan = lan;

        JsonOutput.toJson(privatlakare)
    }

    private setIfAvailable(name, value) {
        if(name) {
            privatlakare.registration[name] = value
        }
    }
}
