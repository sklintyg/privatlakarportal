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
package se.inera.intyg.privatlakarportal.spec

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.core.io.ClassPathResource
import se.inera.intyg.privatlakarportal.spec.util.RestClientFixture
import se.inera.intyg.privatlakarportal.spec.util.RestClientUtils
import static groovyx.net.http.ContentType.JSON



public class SkapaPrivatlakare extends RestClientFixture {

    String godkantMedgivandeVersion;

    String personnummer;
    String fornamn;
    String efternamn;

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

        String responseStatus = ""
    }
	
    public void execute() {
        def restClient = createRestClient()
        if (personnummer && fornamn && efternamn)
            RestClientUtils.login(restClient, fornamn, efternamn, personnummer);
        else
            RestClientUtils.login(restClient);

        // An updated name is needed to create a registration
        restClient.get(
            path: 'user',
	    requestContentType: JSON,
	    headers: ["Cookie":"ROUTEID="+Browser.getRouteId()])

        def resp = restClient.post(
            path: 'registration/create',
            body: createPayload(),
            requestContentType: JSON,
	    headers: ["Cookie":"ROUTEID="+Browser.getRouteId()]
	)
        responseStatus = resp.status
    }

    protected createPayload() {
        overrideDocumentFromFixture()
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
        if(godkantMedgivandeVersion) privatlakare.godkantMedgivandeVersion = godkantMedgivandeVersion;

        JsonOutput.toJson(privatlakare)
    }

    private setIfAvailable(name, value) {
        if(name) {
            privatlakare.registration[name] = value
        }
    }
}
