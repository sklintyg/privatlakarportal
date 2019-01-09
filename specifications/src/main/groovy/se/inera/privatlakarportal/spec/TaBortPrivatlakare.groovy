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

public class TaBortPrivatlakare extends RestClientFixture {

    def restClient = createRestClient()

    String id
    String restPath = 'test/registration/remove/'
    boolean responseStatus

    public void reset() {
    }

    public void execute() {
        RestClientUtils.login(restClient)
        def resp
        if (finns(id, ".1")) {
        resp = restClient.delete(
                path: restPath + id,
                requestContentType: JSON,
                headers: ["Cookie":"ROUTEID=.1"]
        )}
        def resp2
        if (finns(id, ".2")) {
             resp2 = restClient.delete(
                    path: restPath + id,
                    requestContentType: JSON,
                    headers: ["Cookie": "ROUTEID=.2"]
            )
        }
        if (resp == null && resp2 == null) {
            responseStatus = true
        } else {
            responseStatus = (resp != null && resp.status) || (resp2 != null && resp2.status)
        }
    }

    private boolean finns(String pers, def route) {
        def user = restClient.get(
                path: 'test/registration/' + pers,
                requestContentType: JSON,
                headers: ["Cookie":"ROUTEID="+route]
        )
        user.data
    }

    public boolean resultat() {
        responseStatus
    }
}