/*
 * Copyright (C) 2019 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.web.controller.api;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import se.inera.intyg.infra.integration.postnummer.service.PostnummerService;
import se.inera.intyg.privatlakarportal.common.model.RegistrationStatus;
import se.inera.intyg.privatlakarportal.service.RegisterService;
import se.inera.intyg.privatlakarportal.service.model.RegistrationWithHospInformation;
import se.inera.intyg.privatlakarportal.service.model.SaveRegistrationResponseStatus;
import se.inera.intyg.privatlakarportal.web.controller.api.dto.*;

/**
 * Created by pebe on 2015-06-25.
 */
@Api(value = "/registration", description = "REST API för registration-service", produces = MediaType.APPLICATION_JSON)
@RestController
@RequestMapping("/api/registration")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private PostnummerService postnummerService;

    @ApiOperation(value = "getRegistration")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public GetRegistrationResponse getRegistration() {
        RegistrationWithHospInformation registrationWithHospInformation = registerService.getRegistration();
        return new GetRegistrationResponse(registrationWithHospInformation.getRegistration(),
                registrationWithHospInformation.getHospInformation());
    }

    @ApiOperation(value = "createRegistration", nickname = "create")
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json")
    public CreateRegistrationResponse createRegistration(@RequestBody CreateRegistrationRequest request) {
        RegistrationStatus status = registerService.createRegistration(request.getRegistration(), request.getGodkantMedgivandeVersion());
        return new CreateRegistrationResponse(status);
    }

    @ApiOperation(value = "createRegistration", nickname = "save")
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public SaveRegistrationResponse createRegistration(@RequestBody SaveRegistrationRequest request) {
        SaveRegistrationResponseStatus status = registerService.saveRegistration(request.getRegistration());
        return new SaveRegistrationResponse(status);
    }

    @ApiOperation(value = "getHospInformation")
    @RequestMapping(value = "/hospInformation", method = RequestMethod.GET)
    public GetHospInformationResponse getHospInformation() {
        return new GetHospInformationResponse(registerService.getHospInformation());
    }

    @ApiOperation(value = "getOmrade")
    @RequestMapping(value = "/omrade/{postnummer}", method = RequestMethod.GET)
    public GetOmradeResponse getOmrade(@PathVariable("postnummer") String postnummer) {
        return new GetOmradeResponse(postnummerService.getOmradeByPostnummer(postnummer));
    }
}
