package se.inera.privatlakarportal.web.controller.api.dto;

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
