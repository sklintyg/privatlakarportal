package se.inera.privatlakarportal.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.inera.ifv.hsawsresponder.v3.*;
import se.inera.privatlakarportal.auth.PrivatlakarUser;
import se.inera.privatlakarportal.hsa.services.HospPersonService;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.PrivatlakareId;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareIdRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.dto.HospInformation;
import se.inera.privatlakarportal.service.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationRequest;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationResponseStatus;
import se.inera.privatlakarportal.web.controller.api.dto.Registration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceImplTest {

    @Mock
    private PrivatlakareRepository privatlakareRepository;

    @Mock
    private PrivatlakareIdRepository privatlakareidRepository;

    @Mock
    private HospPersonService hospPersonService;

    @Mock
    private UserService userService;


    @InjectMocks
    private RegisterService registerService = new RegisterServiceImpl();

    private GetHospPersonResponseType createGetHospPersonResponse() {
        GetHospPersonResponseType getHospPersonResponseType = new GetHospPersonResponseType();

        getHospPersonResponseType.setSpecialityCodes(new SpecialityCodesType());
        getHospPersonResponseType.setSpecialityNames(new SpecialityNamesType());
        getHospPersonResponseType.setTitleCodes(new TitleCodesType());
        getHospPersonResponseType.setHsaTitles(new HsaTitlesType());

        return getHospPersonResponseType;
    }

    private Registration createValidRegistration() {
        Registration registration = new Registration();

        registration.setVerksamhetensNamn("Test verksamhet");
        registration.setArbetsplatskod("0000000");
        registration.setAgarForm("Privat");
        registration.setAdress("Test adress");
        registration.setPostnummer("12345");
        registration.setPostort("Test postort");
        registration.setTelefonnummer("123456789");
        registration.setEpost("test@example.com");
        registration.setBefattning("201010");
        registration.setVerksamhetstyp("10");
        registration.setVardform("01");
        registration.setLan("Test län");
        registration.setKommun("Test kommun");

        return registration;
    }

    @Test(expected = PrivatlakarportalServiceException.class)
    public void testInvalidCreateRegistration() {
        Registration registration = new Registration();
        registerService.createRegistration(registration);
    }

    @Test
    public void testCreateRegistrationLakare() {

        PrivatlakareId privatlakareId = new PrivatlakareId();
        privatlakareId.setId(1);
        when(privatlakareidRepository.save(any(PrivatlakareId.class))).thenReturn(privatlakareId);

        when(privatlakareRepository.save(any(Privatlakare.class))).then(AdditionalAnswers.returnsFirstArg());

        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212"));

        GetHospPersonResponseType hospPersonResponse = createGetHospPersonResponse();
        hospPersonResponse.getTitleCodes().getTitleCode().add("LK");
        hospPersonResponse.getHsaTitles().getHsaTitle().add("Läkare");
        when(hospPersonService.getHospPerson("1912121212")).thenReturn(hospPersonResponse);

        Registration registration = createValidRegistration();
        CreateRegistrationResponseStatus response = registerService.createRegistration(registration);

        verify(privatlakareRepository).save(any(Privatlakare.class));
        verify(hospPersonService, times(0)).handleCertifier(any(String.class), any(String.class));
        assertEquals(response, CreateRegistrationResponseStatus.AUTHORIZED);
    }

    @Test
    public void testCreateRegistrationEjLakare() {

        PrivatlakareId privatlakareId = new PrivatlakareId();
        privatlakareId.setId(1);
        when(privatlakareidRepository.save(any(PrivatlakareId.class))).thenReturn(privatlakareId);

        when(privatlakareRepository.save(any(Privatlakare.class))).then(AdditionalAnswers.returnsFirstArg());

        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212"));

        when(hospPersonService.getHospPerson("1912121212")).thenReturn(createGetHospPersonResponse());

        Registration registration = createValidRegistration();
        CreateRegistrationResponseStatus response = registerService.createRegistration(registration);

        verify(privatlakareRepository).save(any(Privatlakare.class));
        verify(hospPersonService, times(0)).handleCertifier(any(String.class), any(String.class));
        assertEquals(response, CreateRegistrationResponseStatus.NOT_AUTHORIZED);
    }

    @Test
    public void testCreateRegistrationEjIHosp() {

        PrivatlakareId privatlakareId = new PrivatlakareId();
        privatlakareId.setId(1);
        when(privatlakareidRepository.save(any(PrivatlakareId.class))).thenReturn(privatlakareId);

        when(privatlakareRepository.save(any(Privatlakare.class))).then(AdditionalAnswers.returnsFirstArg());

        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212"));

        when(hospPersonService.getHospPerson("1912121212")).thenReturn(null);

        when(hospPersonService.handleCertifier(eq("1912121212"), any(String.class))).thenReturn(true);

        Registration registration = createValidRegistration();
        CreateRegistrationResponseStatus response = registerService.createRegistration(registration);

        verify(privatlakareRepository).save(any(Privatlakare.class));
        verify(hospPersonService).handleCertifier(eq("1912121212"), any(String.class));
        assertEquals(response, CreateRegistrationResponseStatus.AUTHENTICATION_INPROGRESS);
    }

    @Test
    public void getHospInformation() {

        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212"));

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

        HospInformation hospInformation = registerService.getHospInformation();

        assertEquals(hospInformation.getPersonalPrescriptionCode(), "0000000");
        assertEquals(hospInformation.getHsaTitles().size(), 1);
        assertEquals(hospInformation.getHsaTitles().get(0), "Test title");
        assertEquals(hospInformation.getSpecialityNames().size(), 1);
        assertEquals(hospInformation.getSpecialityNames().get(0), "Test speciality");
    }
}