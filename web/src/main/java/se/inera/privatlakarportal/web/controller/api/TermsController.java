package se.inera.privatlakarportal.web.controller.api;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import se.inera.privatlakarportal.integration.terms.services.WebcertTermsService;
import se.inera.privatlakarportal.service.TermsService;
import se.inera.privatlakarportal.web.controller.api.dto.*;

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
