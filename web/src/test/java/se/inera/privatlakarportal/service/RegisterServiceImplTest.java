package se.inera.privatlakarportal.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
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
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.PrivatlakareId;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareIdRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.exception.PrivatlakarportalServiceExceptionMatcher;
import se.inera.privatlakarportal.service.model.RegistrationStatus;
import se.inera.privatlakarportal.service.model.SaveRegistrationResponseStatus;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.service.model.Registration;
import static org.mockito.Mockito.*;

import javax.xml.ws.WebServiceException;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceImplTest {

    @Mock
    private PrivatlakareRepository privatlakareRepository;

    @Mock
    private PrivatlakareIdRepository privatlakareidRepository;

    @Mock
    private HospUpdateService hospUpdateService;

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

    @Before
    public void setup() {
        PrivatlakarUser privatlakarUser = new PrivatlakarUser("1912121212", "Test User");
        privatlakarUser.updateNameFromPuService("Test User");
        when(userService.getUser()).thenReturn(privatlakarUser);
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

        when(privatlakareRepository.findByPersonId("1912121212")).thenReturn(new Privatlakare());

        Registration registration = createValidRegistration();
        RegistrationStatus response = registerService.createRegistration(registration);
    }

    @Test
    public void testCreateRegistrationLakare() {

        PrivatlakareId privatlakareId = new PrivatlakareId();
        privatlakareId.setId(1);
        when(privatlakareidRepository.save(any(PrivatlakareId.class))).thenReturn(privatlakareId);

        GetHospPersonResponseType hospPersonResponse = createGetHospPersonResponse();
        hospPersonResponse.getTitleCodes().getTitleCode().add("LK");
        hospPersonResponse.getHsaTitles().getHsaTitle().add("Läkare");
        when(hospUpdateService.updateHospInformation(any(Privatlakare.class))).thenReturn(RegistrationStatus.AUTHORIZED);

        Registration registration = createValidRegistration();
        RegistrationStatus response = registerService.createRegistration(registration);

        verify(privatlakareRepository).save(any(Privatlakare.class));
        assertEquals(response, RegistrationStatus.AUTHORIZED);
    }

    @Test
    public void testCreateRegistrationEjLakare() {

        PrivatlakareId privatlakareId = new PrivatlakareId();
        privatlakareId.setId(1);
        when(privatlakareidRepository.save(any(PrivatlakareId.class))).thenReturn(privatlakareId);

        when(hospUpdateService.updateHospInformation(any(Privatlakare.class))).thenReturn(RegistrationStatus.NOT_AUTHORIZED);

        Registration registration = createValidRegistration();
        RegistrationStatus response = registerService.createRegistration(registration);

        verify(privatlakareRepository).save(any(Privatlakare.class));
        assertEquals(response, RegistrationStatus.NOT_AUTHORIZED);
    }

    @Test
    public void testCreateRegistrationEjIHosp() {

        PrivatlakareId privatlakareId = new PrivatlakareId();
        privatlakareId.setId(1);
        when(privatlakareidRepository.save(any(PrivatlakareId.class))).thenReturn(privatlakareId);

        when(hospUpdateService.updateHospInformation(any(Privatlakare.class))).thenReturn(RegistrationStatus.WAITING_FOR_HOSP);

        Registration registration = createValidRegistration();
        RegistrationStatus response = registerService.createRegistration(registration);

        verify(privatlakareRepository).save(any(Privatlakare.class));
        assertEquals(response, RegistrationStatus.WAITING_FOR_HOSP);
    }

    @Test
    public void testCreateRegistrationEjIPUService() {

        thrown.expect(PrivatlakarportalServiceException.class );
        thrown.expect(PrivatlakarportalServiceExceptionMatcher.hasErrorCode(PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM));

        when(userService.getUser()).thenReturn(new PrivatlakarUser("1912121212", "Test User"));

        Registration registration = createValidRegistration();
        RegistrationStatus response = registerService.createRegistration(registration);

        verifyNoMoreInteractions(privatlakareRepository);
        verifyNoMoreInteractions(hospUpdateService);
    }

    @Test
    public void testSavePrivatlakare() {

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

        when(privatlakareRepository.findByPersonId("1912121212")).thenReturn(null);

        Registration registration = createValidRegistration();
        SaveRegistrationResponseStatus response = registerService.saveRegistration(registration);
    }

    @Test
    public void testRemovePrivatlakare() {
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
