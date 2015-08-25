package se.inera.privatlakarportal.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.inera.privatlakarportal.integration.terms.services.TermsService;
import se.inera.privatlakarportal.web.controller.api.dto.*;

/**
 * Created by pebe on 2015-08-21.
 */
@RestController
@RequestMapping("/api/terms")
public class TermsController {

    @Autowired
    private TermsService termsService;

    @RequestMapping(value = "/webcert")
    public GetTermsResponse getTerms() {
        return new GetTermsResponse(termsService.getTerms());
    }

}
