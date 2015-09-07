package se.inera.privatlakarportal.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.inera.ifv.hsawsresponder.v3.*;
import se.inera.privatlakarportal.auth.PrivatlakarUser;
import se.inera.privatlakarportal.hsa.services.HospPersonService;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.PrivatlakareId;
import se.inera.privatlakarportal.service.model.HospInformation;
import se.inera.privatlakarportal.service.model.Registration;
import se.inera.privatlakarportal.service.model.RegistrationStatus;

import javax.xml.ws.WebServiceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by pebe on 2015-09-04.
 */
@RunWith(MockitoJUnitRunner.class)
public class HospUpdateServiceImplTest {

    @Mock
    private HospPersonService hospPersonService;

    @Mock
    private UserService userService;

    @InjectMocks
    private HospUpdateService hospUpdateService = new HospUpdateServiceImpl();

    @Before
    public void setup() {
        PrivatlakarUser privatlakarUser = new PrivatlakarUser("1912121212", "Test User");
        privatlakarUser.updateNameFromPuService("Test User");
        when(userService.getUser()).thenReturn(privatlakarUser);
    }

    @Test
    public void getHospInformation() {

        GetHospPersonResponseType hospPersonResponse = new GetHospPersonResponseType();
        hospPersonResponse.setPersonalIdentityNumber("1912121212");
        hospPersonResponse.setPersonalPrescriptionCode("0000000");
        HsaTitlesType hasTitles = new HsaTitlesType();
        hasTitles.getHsaTitle().add("Test title");
        hospPersonResponse.setHsaTitles(hasTitles);
        SpecialityNamesType specialityNamesType = new SpecialityNamesType();
        specialityNamesType.getSpecialityName().add("Test speciality");
        hospPersonResponse.setSpecialityNames(specialityNamesType);

        when(hospPersonService.getHospPerson("1912121212")).thenReturn(hospPersonResponse);

        HospInformation hospInformation = hospUpdateService.getHospInformation();

        assertEquals(hospInformation.getPersonalPrescriptionCode(), "0000000");
        assertEquals(hospInformation.getHsaTitles().size(), 1);
        assertEquals(hospInformation.getHsaTitles().get(0), "Test title");
        assertEquals(hospInformation.getSpecialityNames().size(), 1);
        assertEquals(hospInformation.getSpecialityNames().get(0), "Test speciality");
    }

    @Test
    public void getHospInformationNotInHosp() {

        when(hospPersonService.getHospPerson("1912121212")).thenReturn(null);

        HospInformation hospInformation = hospUpdateService.getHospInformation();

        assertNull(hospInformation);
    }

    @Test
    public void testUpdateHospInformationKanEjKontaktaHSA1() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId("1912121212");

        when(hospPersonService.getHospPerson("1912121212")).thenThrow(new WebServiceException("Could not send message"));

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare);

        verify(hospPersonService).getHospPerson("1912121212");
        verifyNoMoreInteractions(hospPersonService);
        assertEquals(response, RegistrationStatus.WAITING_FOR_HOSP);
    }

    @Test
    public void testUpdateHospInformationKanEjKontaktaHSA2() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId("1912121212");

        when(hospPersonService.getHospPerson("1912121212")).thenReturn(null);

        when(hospPersonService.handleCertifier(eq("1912121212"), any(String.class))).thenThrow(new WebServiceException("Could not send message"));

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare);

        verify(hospPersonService).getHospPerson("1912121212");
        verify(hospPersonService).handleCertifier(eq("1912121212"), any(String.class));
        verifyNoMoreInteractions(hospPersonService);
        assertEquals(response, RegistrationStatus.WAITING_FOR_HOSP);
    }

    @Test
    public void testUpdateHospInformationEjLakare() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId("1912121212");

        when(hospPersonService.getHospPerson("1912121212")).thenReturn(createGetHospPersonResponse());

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare);

        verify(hospPersonService, times(0)).handleCertifier(any(String.class), any(String.class));
        assertEquals(response, RegistrationStatus.NOT_AUTHORIZED);
    }

    @Test
    public void testUpdateHospInformationEjIHosp() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId("1912121212");

        when(hospPersonService.getHospPerson("1912121212")).thenReturn(null);

        when(hospPersonService.handleCertifier(eq("1912121212"), any(String.class))).thenReturn(true);

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare);

        verify(hospPersonService).handleCertifier(eq("1912121212"), any(String.class));
        assertEquals(response, RegistrationStatus.WAITING_FOR_HOSP);
    }

    @Test
    public void testUpdateHospInformationLakare() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId("1912121212");

        GetHospPersonResponseType hospPersonResponse = createGetHospPersonResponse();
        hospPersonResponse.getTitleCodes().getTitleCode().add("LK");
        hospPersonResponse.getHsaTitles().getHsaTitle().add("Läkare");
        when(hospPersonService.getHospPerson("1912121212")).thenReturn(hospPersonResponse);

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare);

        verify(hospPersonService, times(0)).handleCertifier(any(String.class), any(String.class));
        assertEquals(response, RegistrationStatus.AUTHORIZED);
    }

    private GetHospPersonResponseType createGetHospPersonResponse() {
        GetHospPersonResponseType getHospPersonResponseType = new GetHospPersonResponseType();

        getHospPersonResponseType.setSpecialityCodes(new SpecialityCodesType());
        getHospPersonResponseType.setSpecialityNames(new SpecialityNamesType());
        getHospPersonResponseType.setTitleCodes(new TitleCodesType());
        getHospPersonResponseType.setHsaTitles(new HsaTitlesType());

        return getHospPersonResponseType;
    }
}
