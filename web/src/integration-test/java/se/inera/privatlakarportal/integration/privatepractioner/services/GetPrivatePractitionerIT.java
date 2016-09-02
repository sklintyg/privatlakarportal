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
public class GetPrivatePractitionerIT extends BaseIntegrationTest {

    private static final String BASE = "Envelope.Body.GetPrivatePractitionerResponse.";
    private static final String GET_PRIVATE_PRACTITIONER_V1_0 = "services/get-private-practitioner/v1.0";
    private static final String PNR = "191212121212";
    public static final String PNR_OKANT = "inte-ett-personnummer-i-varje-fall";

    private ST requestTemplate;
    private STGroup templateGroup;

    @Before
    public void setup() throws IOException {
        // Setup String template resource
        templateGroup = new STGroupFile("integrationtestTemplates/getPrivatePractitioner.v1.stg");
        requestTemplate = templateGroup.getInstanceOf("request");

    }

    @Test
    public void testGetPrivatePractitioner() throws Exception {
        requestTemplate.add("data", new GetData(PNR));
        given().body(requestTemplate.render())
                .when()
                .post(RestAssured.baseURI + GET_PRIVATE_PRACTITIONER_V1_0)
                .then().statusCode(200)
                .rootPath(BASE)
                .body("resultCode", is(ResultCodeEnum.OK.value()))
                .body("hoSPerson.hsaId.@extension", is("SE165565594230-WEBCERTBOOT1"))
                .body("hoSPerson.personId.@extension", is("191212121212"))
                .body("hoSPerson.enhet.enhetsnamn", is("TestEnhet"));
    }

    @Test
    public void testGetPrivatePractitionerThatDoesNotExist() throws Exception {
        requestTemplate.add("data", new GetData(PNR_OKANT));
        given().body(requestTemplate.render())
                .when()
                .post(RestAssured.baseURI + GET_PRIVATE_PRACTITIONER_V1_0)
                .then().statusCode(200)
                .rootPath(BASE)
                .body("resultCode", is(ResultCodeEnum.ERROR.value()))
                .body("resultText", is("No private practitioner with personal identity number: " + PNR_OKANT + " exists."));
    }

    private class GetData {

        public final String personId;

        public GetData(String personId) {
            this.personId = personId;
        }
    }

}
