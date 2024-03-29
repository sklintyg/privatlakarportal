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
package se.inera.intyg.privatlakarportal.auth;

import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.staxutils.StaxUtils;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;

/**
 * Base class for tests that needs to build a SAMLCredential from sample XML documents.
 *
 * Created by eriklupander on 2015-08-26.
 */
public abstract class BaseSAMLCredentialTest {

    protected static Assertion assertionPrivatlakare;
    protected static Assertion assertionLandstingslakare;
    protected static Assertion assertionUnknownAuthCtx;

    private static boolean bootstrapped = false;

    protected static void bootstrapSamlAssertions() throws Exception {

        if (!bootstrapped) {
            DefaultBootstrap.bootstrap();
            bootstrapped = true;
        }

        if (assertionPrivatlakare == null) {
            XMLObject responseXmlObj = readSamlDocument("CGIElegAssertiontest/sample-saml2-response-bankid.xml");
            Response response = (Response) responseXmlObj;
            assertionPrivatlakare = response.getAssertions().get(0);
        }

        if (assertionLandstingslakare == null) {
            XMLObject responseXmlObj = readSamlDocument("CGIElegAssertiontest/saml-assertion-with-title-code-lakare.xml");
            assertionLandstingslakare = (Assertion) responseXmlObj;
        }

        if (assertionUnknownAuthCtx == null) {
            XMLObject responseXmlObj = readSamlDocument("CGIElegAssertiontest/sample-saml2-response-unknown-auth-ctx.xml");
            Response response = (Response) responseXmlObj;
            assertionUnknownAuthCtx = response.getAssertions().get(0);
        }
    }

    private static XMLObject readSamlDocument(String docPath) throws Exception {
        Document doc = StaxUtils.read(new StreamSource(new ClassPathResource(
            docPath).getInputStream()));
        org.w3c.dom.Element documentElement = doc.getDocumentElement();

        UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
        Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(documentElement);
        return unmarshaller.unmarshall(documentElement);
    }

}
