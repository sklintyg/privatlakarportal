package se.inera.privatlakarportal.integration.privatepractioner.services;

import com.jayway.restassured.RestAssured;
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
    private STGroup templateGroup;

    @Before
    public void setup() throws IOException {
        // Setup String template resource
        templateGroup = new STGroupFile("integrationtestTemplates/validatePrivatePractitioner.v1.stg");
        requestTemplate = templateGroup.getInstanceOf("request");

    }

    @Test
    public void testValidatePrivatePractitioner() throws Exception {
        requestTemplate.add("data", new ValidationData(PNR));
        given().body(requestTemplate.render())
                .when()
                .post(RestAssured.baseURI + VALIDATE_PRIVATE_PRACTITIONER_V1_0)
                .then().statusCode(200)
                .rootPath(BASE)
                .body("resultCode", is(ResultCodeEnum.OK.value()));

    }

    @Test
    public void testValidatePrivatePractitionerThatIsNotValid() throws Exception {
        requestTemplate.add("data", new ValidationData(PNR_OKANT));
        given().body(requestTemplate.render())
                .when()
                .post(RestAssured.baseURI + VALIDATE_PRIVATE_PRACTITIONER_V1_0)
                .then().statusCode(200)
                .rootPath(BASE)
                .body("resultCode", is(ResultCodeEnum.ERROR.value()))
                .body("resultText", is("No private practitioner with personal identity number: " + PNR_OKANT + " exists."));

    }

    private class ValidationData {

        public final String personId;

        public ValidationData(String personId) {
            this.personId = personId;
        }
    }

}
