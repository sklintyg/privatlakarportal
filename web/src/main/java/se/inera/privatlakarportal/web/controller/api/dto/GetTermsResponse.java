package se.inera.privatlakarportal.web.controller.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.inera.privatlakarportal.integration.terms.services.dto.Terms;

/**
 * Created by pebe on 2015-08-25.
 */
@ApiModel(description = "Response-object för Terms")
public class GetTermsResponse {

    @ApiModelProperty(name = "terms", dataType = "Terms")
    private Terms terms;

    public GetTermsResponse(Terms terms) {
        this.terms = terms;
    }

    public Terms getTerms() {
        return terms;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

}
