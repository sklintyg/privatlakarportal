package se.inera.privatlakarportal.web.controller.api.dto;

import org.apache.commons.lang.StringUtils;

public class CreateRegistrationRequest {

    Registration registration;

    public CreateRegistrationRequest() {
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}
