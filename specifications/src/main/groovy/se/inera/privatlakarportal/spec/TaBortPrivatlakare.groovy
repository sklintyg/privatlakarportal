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