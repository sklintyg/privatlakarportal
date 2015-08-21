package se.inera.privatlakarportal.spec

import se.inera.privatlakarportal.spec.util.RestClientFixture

import static groovyx.net.http.ContentType.JSON

public class TaBortPrivatlakare extends RestClientFixture {

    def restClient = createRestClient()

    String id
    String restPath = 'registration/'

	public void reset() {
	}
	
    public void execute() {
        Exception pendingException
        String failedIds = ""
        try {
        restClient.delete(
                path: restPath + id,
                requestContentType: JSON
        )
        } catch(e) {
            failedIds += id + ","
            if (!pendingException) {
                pendingException = e
            }
        }
        if (pendingException) {
            throw new Exception("Kunde inte ta bort " + failedIds, pendingException)
        }
    }

    public void taBortAllaPrivatlakare() {
        restClient.delete(
            path: restPath,
            requestContentType: JSON
        )
    }
}
