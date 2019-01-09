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
package se.inera.intyg.privatlakarportal.spec

import se.inera.intyg.privatlakarportal.spec.util.RestClientFixture
import se.inera.intyg.privatlakarportal.spec.util.RestClientUtils

import static groovyx.net.http.ContentType.JSON

/**
 * Created by pebe on 2015-09-04.
 */
class HospUppdatering extends RestClientFixture {
    String restPath = 'test/hosp/'

    public String taBortHospInformation(String id) {
        def restClient = createRestClient()
        def resp = restClient.delete(
            path: restPath + "remove/" + id,
            requestContentType: JSON,
            headers: ["Cookie":"ROUTEID="+Browser.getRouteId()]
        )
        resp.status
    }

    public String korHospUppdatering() {
        def restClient = createRestClient()
        def resp = restClient.post(
            path: restPath + "update",
            requestContentType: JSON,
            headers: ["Cookie":"ROUTEID="+Browser.getRouteId()]
        )
        resp.status
    }

    public boolean harLakarbehorighet() {
        def restClient = createRestClient()
        RestClientUtils.login(restClient, "Björn Anders Daniel", "Pärsson", "195206172339");
        def resp = restClient.get(
            path: "/api/registration",
            headers: ["Cookie":"ROUTEID="+Browser.getRouteId()]
        )
        resp.data.hospInformation != null && resp.data.hospInformation.hsaTitles != null && resp.data.hospInformation.hsaTitles.contains("Läkare")
    }

    public String loggaInGenomWebcert(String personId) {
        def restClient = createRestClient()
        def resp = restClient.post(
            path: 'test/webcert/validatePrivatePractitioner/' + personId,
            requestContentType: JSON,
            headers: ["Cookie":"ROUTEID="+Browser.getRouteId()]
        )
        resp.data.resultCode
    }
}
