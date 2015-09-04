package se.inera.privatlakarportal.web.controller.api.dto;

import javafx.scene.paint.Color;

/**
 * Created by pebe on 2015-08-28.
 */
public class GetConfigResponse {
    private String webcertUrl;
    private String webcertStartUrl;

    public GetConfigResponse(String webcertUrl, String webcertStartUrl) {
        this.webcertUrl = webcertUrl;
        this.webcertStartUrl = webcertStartUrl;
    }

    public String getWebcertUrl() {
        return webcertUrl;
    }

    public String getWebcertStartUrl() {
        return webcertStartUrl;
    }
}
