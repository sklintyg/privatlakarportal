package se.inera.privatlakarportal.web.controller.api.dto;

/**
 * Created by pebe on 2015-08-28.
 */
public class GetConfigResponse {
    private String webcertUrl;
    public GetConfigResponse(String webcertUrl) {
        this.webcertUrl = webcertUrl;
    }

    public String getWebcertUrl() {
        return webcertUrl;
    }
}
