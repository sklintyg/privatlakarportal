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
package se.inera.privatlakarportal.integration.terms.services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.inera.privatlakarportal.integration.terms.services.TermsWebServiceCalls;
import se.inera.privatlakarportal.integration.terms.stub.TermsWebServiceStub;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionerterms.v1.rivtabp21.GetPrivatePractitionerTermsResponderInterface;

@Configuration
public class TermsWebServiceTestConfig {

    @Bean
    public GetPrivatePractitionerTermsResponderInterface termsWebServiceClient() {
        return new TermsWebServiceStub();
    }

    @Bean
    public TermsWebServiceCalls testTermsWSCalls() {
        return new TermsWebServiceCalls();
    }
}
