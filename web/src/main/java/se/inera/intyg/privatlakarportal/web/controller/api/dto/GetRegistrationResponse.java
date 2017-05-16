/**
 * Copyright (C) 2016 Inera AB (http://www.inera.se)
 *
 * This file is part of privatlakarportal (https://github.com/sklintyg/privatlakarportal).
 *
 * privatlakarportal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * privatlakarportal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.web.controller.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.inera.intyg.privatlakarportal.common.model.Registration;
import se.inera.intyg.privatlakarportal.service.model.HospInformation;

/**
 * Created by pebe on 2015-08-06.
 */

@ApiModel(description = "Response-object för getRegistration")
public class GetRegistrationResponse {

    @ApiModelProperty(name = "registration", dataType = "Registration")
    private Registration registration;

    @ApiModelProperty(name = "hospInformation", dataType = "HospInformation")
    private HospInformation hospInformation;

    public GetRegistrationResponse(Registration registration, HospInformation hospInformation) {
        this.registration = registration;
        this.hospInformation = hospInformation;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public HospInformation getHospInformation() {
        return hospInformation;
    }

    public void setHospInformation(HospInformation hospInformation) {
        this.hospInformation = hospInformation;
    }
}