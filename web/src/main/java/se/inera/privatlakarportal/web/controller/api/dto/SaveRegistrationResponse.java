package se.inera.privatlakarportal.web.controller.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.inera.privatlakarportal.service.model.SaveRegistrationResponseStatus;

/**
 * Created by pebe on 2015-08-17.
 */
@ApiModel(description = "Response-obect för SaveRegistration")
public class SaveRegistrationResponse {

    @ApiModelProperty(name = "status", dataType = "SaveRegistrationResponseStatus")
    private SaveRegistrationResponseStatus status;

    public SaveRegistrationResponse(SaveRegistrationResponseStatus status) {
        this.status = status;
    }

    public SaveRegistrationResponseStatus getStatus() {
        return status;
    }

    public void setStatus(SaveRegistrationResponseStatus status) {
        this.status = status;
    }
}
