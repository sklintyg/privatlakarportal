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
        resp.status == 200 ? resp.status : resp2.status
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
