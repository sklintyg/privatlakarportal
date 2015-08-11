package se.inera.privatlakarportal.hsa.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.inera.ifv.hsawsresponder.v3.*;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HospPersonServiceTest {

    private static final String VALID_PERSON_ID = "191212-1212";
    private static final String INVALID_PERSON_ID = "000000-0000";

    @Mock
    HSAWebServiceCalls hsaWebServiceCalls;

    @InjectMocks
    HospPersonServiceImpl hospPersonService;

    @Before
    public void setupExpectations() {

        GetHospPersonType validParams = new GetHospPersonType();
        validParams.setPersonalIdentityNumber(VALID_PERSON_ID);

        GetHospPersonType invalidParams = new GetHospPersonType();
        invalidParams.setPersonalIdentityNumber(INVALID_PERSON_ID);

        GetHospPersonResponseType response = new GetHospPersonResponseType();
        when(hsaWebServiceCalls.callGetHospPerson(validParams)).thenReturn(response);

        when(hsaWebServiceCalls.callGetHospPerson(invalidParams)).thenReturn(null);
    }

    @Test
    public void testGetHsaPersonInfoWithValidPerson() {

        GetHospPersonResponseType res = hospPersonService.getHospPerson(VALID_PERSON_ID);

        assertNotNull(res);
    }

    @Test
    public void testGetHsaPersonInfoWithInvalidPerson() {

        GetHospPersonResponseType res = hospPersonService.getHospPerson(INVALID_PERSON_ID);

        assertNull(res);
    }

}
