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
import se.inera.intyg.privatlakarportal.integration.terms.services.WebcertTermsService;
import se.inera.intyg.privatlakarportal.service.TermsService;
import se.inera.intyg.privatlakarportal.web.controller.api.dto.*;

/**
 * Created by pebe on 2015-08-21.
 */
@Api(value = "/terms", description = "REST API för term-service", produces = MediaType.APPLICATION_JSON)
@RestController
@RequestMapping("/api/terms")
public class TermsController {

    @Autowired
    private TermsService termsService;

    @Autowired
    private WebcertTermsService webcertTermsService;

    @RequestMapping(value = "/webcert", method = RequestMethod.GET)
    @ApiOperation(value = "getWebcertTerms")
    public GetTermsResponse getWebcertTerms() {
        return new GetTermsResponse(webcertTermsService.getTerms());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "getTerms")
    public GetTermsResponse getTerms() {
        return new GetTermsResponse(termsService.getTerms());
    }
}
