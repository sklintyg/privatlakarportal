package se.inera.privatlakarportal.web.controller.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.inera.privatlakarportal.service.model.HospInformation;

/**
 * Created by pebe on 2015-08-13.
 */
@ApiModel(description = "Response-object för getHospInformation")
public class GetHospInformationResponse {

    @ApiModelProperty(name = "hospInformation", dataType = "HospInformation")
    private HospInformation hospInformation;

    public GetHospInformationResponse(HospInformation hospInformation) {
        this.hospInformation = hospInformation;
    }

    public HospInformation getHospInformation() {
        return hospInformation;
    }

    public void setHospInformation(HospInformation hospInformation) {
        this.hospInformation = hospInformation;
    }

}
