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
package se.inera.privatlakarportal.integration.privatepractioner.services;

import org.junit.Before;
import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import se.riv.infrastructure.directory.privatepractitioner.terms.v1.ResultCodeEnum;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

/**
 * Created by eriklupander on 2016-09-02.
 */
public class ValidatePrivatePractitionerIT extends BaseIntegrationTest {

    private static final String BASE = "Envelope.Body.ValidatePrivatePractitionerResponse.";
    private static final String VALIDATE_PRIVATE_PRACTITIONER_V1_0 = "services/validate-private-practitioner/v1.0";
    private static final String PNR = "191212121212";
    private static final String PNR_OKANT = "okant-pnr";

    private ST requestTemplate;
    private ST brokenTemplate;
    private STGroup templateGroup;

    @Before
    public void setup() throws IOException {
        // Setup String template resource
        templateGroup = new STGroupFile("integrationtestTemplates/validatePrivatePractitioner.v1.stg");
        requestTemplate = templateGroup.getInstanceOf("request");
        brokenTemplate = templateGroup.getInstanceOf("brokenrequest");
    }

    @Test
    public void testValidatePrivatePractitioner() throws Exception {
        requestTemplate.add("data", new ValidationData(PNR));
        given().body(requestTemplate.render())
                .when()
                .post(VALIDATE_PRIVATE_PRACTITIONER_V1_0)
                .then().statusCode(200)
                .rootPath(BASE)
                .body("resultCode", is(ResultCodeEnum.OK.value()));

    }

    @Test
    public void testValidatePrivatePractitionerThatIsNotValid() throws Exception {
        requestTemplate.add("data", new ValidationData(PNR_OKANT));
        given().body(requestTemplate.render())
                .when()
                .post(VALIDATE_PRIVATE_PRACTITIONER_V1_0)
                .then().statusCode(200)
                .rootPath(BASE)
                .body("resultCode", is(ResultCodeEnum.ERROR.value()))
                .body("resultText", is("No private practitioner with personal identity number: " + PNR_OKANT + " exists."));

    }

    @Test
    public void testValidatePrivatePractitionerWithInvalidRequest() throws Exception {
        given().body(brokenTemplate.render())
                .when()
                .post(VALIDATE_PRIVATE_PRACTITIONER_V1_0)
                .then().statusCode(500)
                .rootPath(BASE);
    }

    private class ValidationData {

        public final String personId;

        public ValidationData(String personId) {
            this.personId = personId;
        }
    }

}
