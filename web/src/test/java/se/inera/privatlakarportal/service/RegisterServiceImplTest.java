package se.inera.privatlakarportal.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.HsaTitlesType;
import se.inera.ifv.hsawsresponder.v3.SpecialityCodesType;
import se.inera.ifv.hsawsresponder.v3.SpecialityNamesType;
import se.inera.ifv.hsawsresponder.v3.TitleCodesType;
import se.inera.privatlakarportal.auth.PrivatlakarUser;
import se.inera.privatlakarportal.hsa.services.HospPersonService;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.PrivatlakareId;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareIdRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.service.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.service.exception.PrivatlakarportalServiceExceptionMatcher;
import se.inera.privatlakarportal.service.model.CreateRegistrationResponseStatus;
import se.inera.privatlakarportal.service.model.HospInformation;
import se.inera.privatlakarportal.service.model.Registration;
import se.inera.privatlakarportal.service.model.SaveRegistrationResponseStatus;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void testInvalidCreateRegistration() {

        thrown.expect(PrivatlakarportalServiceException.class );
        thrown.expect(PrivatlakarportalServiceExceptionMatcher.hasErrorCode(PrivatlakarportalErrorCodeEnum.BAD_REQUEST));

        Registration registration = new Registration();
        registerService.createRegistration(registration);
    }

    @Test
    public void testcreatePrivatlakareAlreadyExists() {

        thrown.expect(PrivatlakarportalServiceException.class );
        thrown.expect(PrivatlakarportalServiceExceptionMatcher.hasErrorCode(PrivatlakarportalErrorCodeEnum.ALREADY_EXISTS));

        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212"));

        when(privatlakareRepository.findByPersonId("1912121212")).thenReturn(new Privatlakare());

        Registration registration = createValidRegistration();
        CreateRegistrationResponseStatus response = registerService.createRegistration(registration);
    }

    @Test
    public void testCreateRegistrationLakare() {

        PrivatlakareId privatlakareId = new PrivatlakareId();
        privatlakareId.setId(1);
        when(privatlakareidRepository.save(any(PrivatlakareId.class))).thenReturn(privatlakareId);

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
    public void testSavePrivatlakare() {
        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212"));

        when(privatlakareRepository.findByPersonId("1912121212")).thenReturn(new Privatlakare());

        Registration registration = createValidRegistration();
        SaveRegistrationResponseStatus response = registerService.saveRegistration(registration);

        verify(privatlakareRepository).save(any(Privatlakare.class));
        assertEquals(response, SaveRegistrationResponseStatus.OK);
    }

    @Test
    public void testSavePrivatlakareNotFound() {

        thrown.expect(PrivatlakarportalServiceException.class );
        thrown.expect(PrivatlakarportalServiceExceptionMatcher.hasErrorCode(PrivatlakarportalErrorCodeEnum.NOT_FOUND));

        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212"));

        when(privatlakareRepository.findByPersonId("1912121212")).thenReturn(null);

        Registration registration = createValidRegistration();
        SaveRegistrationResponseStatus response = registerService.saveRegistration(registration);
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

    @Test
    public void getHospInformationNotInHosp() {

        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212"));
        when(hospPersonService.getHospPerson("1912121212")).thenReturn(null);

        HospInformation hospInformation = registerService.getHospInformation();

        assertNull(hospInformation);
    }

    @Test
    public void testRemovePrivatlakare() {
        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212"));

        when(privatlakareRepository.findByPersonId("1912121212")).thenReturn(new Privatlakare());

        Registration registration = createValidRegistration();

        registerService.saveRegistration(registration);

        assertTrue(registerService.removePrivatlakare("1912121212"));
    }

    @Test
    public void testRemoveNonExistingPrivatlakare() {
        assertFalse(registerService.removePrivatlakare("195206142597"));
    }
}
