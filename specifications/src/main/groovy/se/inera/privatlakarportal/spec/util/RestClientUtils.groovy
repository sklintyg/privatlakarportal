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
package se.inera.intyg.privatlakarportal.spec.util

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.URLENC
import groovy.json.JsonOutput
import groovyx.net.http.HttpResponseDecorator
import se.inera.intyg.privatlakarportal.spec.Browser

class RestClientUtils extends RestClientFixture{

    static def login() {
        def restclient = super.createRestClient()
        login(restclient)
    }

    static def login(def restClient, def firstName = "Oskar", def lastName = "Johansson", def personId = "199008252398") {
        def loginData = JsonOutput.toJson([ firstName: firstName, lastName: lastName, personId: personId ])
        def response = restClient.post(
	    path: '/fake',
	    body: "userJsonDisplay=${loginData}",
            headers: ["Cookie":"ROUTEID=" + Browser.getRouteId()],
	    requestContentType : URLENC
	)
        assert response.status == 302
        System.out.println("Using logindata: " + loginData)
    }
}
