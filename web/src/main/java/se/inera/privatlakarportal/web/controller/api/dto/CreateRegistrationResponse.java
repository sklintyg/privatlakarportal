package se.inera.privatlakarportal.web.controller.api.dto;

import se.inera.privatlakarportal.service.model.CreateRegistrationResponseStatus;

public class CreateRegistrationResponse {

    private CreateRegistrationResponseStatus status;

    public CreateRegistrationResponse(CreateRegistrationResponseStatus status) {
        this.status = status;
    }

    public CreateRegistrationResponseStatus getStatus() {
        return status;
    }

    public void setStatus(CreateRegistrationResponseStatus status) {
        this.status = status;
    }
}
