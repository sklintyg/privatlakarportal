package se.inera.privatlakarportal.spec

import se.inera.privatlakarportal.spec.util.RestClientFixture

import static groovyx.net.http.ContentType.JSON

public class TaBortPrivatlakare extends RestClientFixture {

    def restClient = createRestClient()

    String id
    String restPath = 'registration/remove/'
    boolean responseStatus

	public void reset() {
	}
	
    public void execute() {
        Exception pendingException
        String failedIds = ""
        try {
            def resp = restClient.delete(
                    path: restPath + id,
                    requestContentType: JSON
            )
            responseStatus = resp.status
            
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
    
    public boolean resultat() {
       responseStatus
    }
}
