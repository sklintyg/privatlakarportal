package se.inera.privatlakarportal.web.controller.api.dto;

import se.inera.privatlakarportal.service.model.Registration;

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
