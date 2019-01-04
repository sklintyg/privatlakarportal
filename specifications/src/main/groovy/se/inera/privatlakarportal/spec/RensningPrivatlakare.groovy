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

import static groovyx.net.http.ContentType.JSON

/**
 * Created by pebe on 2015-09-30.
 */
class RensningPrivatlakare extends RestClientFixture{

    String restPath = 'test/'

    public String sattRegistreringsdatumForPrivatlakare(String date, String id) {
        def restClient = createRestClient()
        def resp = restClient.post(
                path: restPath + '/registration/setregistrationdate/' + id,
                body: date,
                requestContentType: JSON,
                headers: ["Cookie":"ROUTEID="+Browser.getRouteId()]
        )
        resp.status
    }

    public String korRensning() {
        def restClient = createRestClient()
        def resp = restClient.post(
                path: restPath + '/cleanup/trigger',
                requestContentType: JSON,
                headers: ["Cookie":"ROUTEID="+Browser.getRouteId()]
        )
        resp.status
    }

    public boolean finnsPrivatlakare(String id) {
        def restClient = createRestClient()
        def resp = restClient.get(
                path: restPath + '/registration/' + id,
                requestContentType: JSON,
                headers: ["Cookie":"ROUTEID="+Browser.getRouteId()]
        )
        resp.data
    }
}
