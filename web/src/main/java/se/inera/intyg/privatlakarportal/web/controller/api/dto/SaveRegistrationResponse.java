/*
 * Copyright (C) 2018 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.web.controller.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.inera.intyg.privatlakarportal.service.model.SaveRegistrationResponseStatus;

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
