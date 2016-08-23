package se.inera.privatlakarportal.web.controller.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.inera.privatlakarportal.common.model.RegistrationStatus;

@ApiModel(description = "Response-objekt för registration-tjänst")
public class CreateRegistrationResponse {

    @ApiModelProperty(name = "STATUS", dataType = "RegistrationStatus")
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
