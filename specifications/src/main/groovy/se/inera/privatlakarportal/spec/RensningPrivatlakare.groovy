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
        System.out.println(resp.data)
        resp.data
    }
}
