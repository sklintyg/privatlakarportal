/*
 * Copyright (C) 2022 Inera AB (http://www.inera.se)
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.privatlakarportal.integration.terms.services.WebcertTermsService;
import se.inera.intyg.privatlakarportal.service.TermsService;
import se.inera.intyg.privatlakarportal.web.controller.api.dto.GetTermsResponse;

/**
 * Created by pebe on 2015-08-21.
 */
@Tag(name = "/terms", description = "REST API för term-service")
@RestController
@RequestMapping("/api/terms")
public class TermsController {

    private final TermsService termsService;

    private final WebcertTermsService webcertTermsService;

    public TermsController(TermsService termsService, WebcertTermsService webcertTermsService) {
        this.termsService = termsService;
        this.webcertTermsService = webcertTermsService;
    }

    @RequestMapping(value = "/webcert", method = RequestMethod.GET)
    @Operation(summary = "getWebcertTerms")
    public GetTermsResponse getWebcertTerms() {
        return new GetTermsResponse(webcertTermsService.getTerms());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @Operation(summary = "getTerms")
    public GetTermsResponse getTerms() {
        return new GetTermsResponse(termsService.getTerms());
    }
}
