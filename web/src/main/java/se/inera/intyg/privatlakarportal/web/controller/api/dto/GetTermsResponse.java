/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
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

import io.swagger.v3.oas.annotations.media.Schema;
import se.inera.intyg.privatlakarportal.integration.terms.services.dto.Terms;

/**
 * Created by pebe on 2015-08-25.
 */
@Schema(description = "Response-object för Terms")
public class GetTermsResponse {

    @Schema(name = "terms", type = "Terms")
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
