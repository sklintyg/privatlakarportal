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

import static groovyx.net.http.ContentType.JSON
import se.inera.intyg.privatlakarportal.spec.util.RestClientFixture

/**
 * Created by pebe on 2015-09-04.
 */
class RegistreringsMail extends RestClientFixture {
    String restPath = 'stub/mails'

    String responseValue

    boolean mottaget

    public String rensaMailStubbe() {
        def restClient = createRestClient()
        def resp = restClient.delete(
            path: restPath + "/clear",
            requestContentType: JSON,
            headers: ["Cookie":"ROUTEID=.1"]
        )
        def resp2 = restClient.delete(
                path: restPath + "/clear",
                requestContentType: JSON,
                headers: ["Cookie":"ROUTEID=.2"]
        )
         resp.status || resp2.status
    }

    public String mailHarSkickats(String id) {
        def restClient = createRestClient()
        def node1 = restClient.get(
            path: restPath,
            requestContentType: JSON,
            headers: ["Cookie":"ROUTEID=.1"]
        )
        def node2 = restClient.get(
                path: restPath,
                requestContentType: JSON,
                headers: ["Cookie":"ROUTEID=.2"]
        )

        mottaget = node1.data.containsKey(id) || node2.data.containsKey(id)
        responseValue = node1.data.get(id) != null ? node1.data.get(id) : node2.data.get(id)
        //resp.data.hospInformation != null && resp.data.hospInformation.hsaTitles != null && resp.data.hospInformation.hsaTitles.contains("Läkare")
    }

    public String mailInnehåll() {
        responseValue
    }

    public Boolean mailMottaget() {
        mottaget
    }
}
