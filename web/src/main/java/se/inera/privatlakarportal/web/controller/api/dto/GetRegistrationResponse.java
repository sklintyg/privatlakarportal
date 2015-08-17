package se.inera.privatlakarportal.web.controller.api.dto;

import se.inera.privatlakarportal.service.dto.Registration;

/**
 * Created by pebe on 2015-08-06.
 */
public class GetRegistrationResponse {

    private Registration registration;

    public GetRegistrationResponse(Registration registration) {
        this.registration = registration;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}
