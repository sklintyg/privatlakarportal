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
package se.inera.privatlakarportal.integration.terms.stub;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionerterms.v1.rivtabp21.GetPrivatePractitionerTermsResponderInterface;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionertermsresponder.v1.GetPrivatePractitionerTermsResponseType;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionertermsresponder.v1.GetPrivatePractitionerTermsType;
import se.riv.infrastructure.directory.privatepractitioner.terms.v1.AvtalType;

/**
 * Created by pebe on 2015-08-25.
 */
public class TermsWebServiceStub implements GetPrivatePractitionerTermsResponderInterface {

    private static final Logger LOG = LoggerFactory.getLogger(TermsWebServiceStub.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public GetPrivatePractitionerTermsResponseType getPrivatePractitionerTerms(String s, GetPrivatePractitionerTermsType getPrivatePractitionerTermsType) {

        AvtalType avtalType = new AvtalType();
        avtalType.setAvtalVersion(1);
        avtalType.setAvtalVersionDatum(LocalDate.parse("2015-09-30").atStartOfDay());

        String fileEncoding = "UTF-8";
        String fileUrl = "classpath:bootstrap-webcertvillkor/webcertvillkor.html";

        LOG.debug("Loading terms file '{}' using encoding '{}'", fileUrl, fileEncoding);

        String avtalText;
        try {
            Resource resource = resourceLoader.getResource(fileUrl);

            if (!resource.exists()) {
                LOG.error("Could not load avtal file since the resource '{}' does not exist", fileUrl);
            } else {
                avtalText = FileUtils.readFileToString(resource.getFile());
                avtalType.setAvtalText(avtalText);
            }

        } catch (IOException ioe) {
            LOG.error("IOException occured when loading avtal file '{}'", fileUrl);
            throw new RuntimeException("Error occured when loading avtal file", ioe);
        }

        GetPrivatePractitionerTermsResponseType response = new GetPrivatePractitionerTermsResponseType();
        response.setAvtal(avtalType);

        return response;
    }
}
