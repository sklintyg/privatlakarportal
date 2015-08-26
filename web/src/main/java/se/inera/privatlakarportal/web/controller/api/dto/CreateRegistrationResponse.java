package se.inera.privatlakarportal.web.controller.api.dto;

import se.inera.privatlakarportal.service.model.RegistrationStatus;

public class CreateRegistrationResponse {

    private RegistrationStatus status;

    public CreateRegistrationResponse(RegistrationStatus status) {
        this.status = status;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }
}
