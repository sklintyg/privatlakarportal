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
package se.inera.privatlakarportal.integration.terms.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionerterms.v1.rivtabp21.GetPrivatePractitionerTermsResponderInterface;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionertermsresponder.v1.GetPrivatePractitionerTermsResponseType;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionertermsresponder.v1.GetPrivatePractitionerTermsType;

@Service
public class TermsWebServiceCalls {

    @Autowired
    private GetPrivatePractitionerTermsResponderInterface termsWebServiceClient;

    private static final Logger LOG = LoggerFactory.getLogger(TermsWebServiceCalls.class);

    public GetPrivatePractitionerTermsResponseType getPrivatePractitionerTerms() {
        try {
            GetPrivatePractitionerTermsType parameters = new GetPrivatePractitionerTermsType();
            return termsWebServiceClient.getPrivatePractitionerTerms("", parameters);
        } catch (Exception ex) {
            LOG.error("Failed to call getPrivatePractitionerTerms");
            Throwables.propagate(ex);
            return null;
        }
    }

}
