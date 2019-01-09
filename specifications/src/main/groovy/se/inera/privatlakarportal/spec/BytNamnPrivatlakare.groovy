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

public class BytNamnPrivatlakare extends RestClientFixture {

    def restClient = createRestClient()

    String id
    String namn
    String restPath = 'test/registration/setname/'
    boolean responseStatus

    public void reset() {
    }

    public void execute() {
        RestClientUtils.login(restClient)
        def resp = restClient.post(
                path: restPath + id,
                body: namn,
                requestContentType: JSON
        )
        responseStatus = resp.status
    }

    public boolean resultat() {
        responseStatus
    }
}
