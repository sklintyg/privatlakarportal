package se.inera.privatlakarportal.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.HsaTitlesType;
import se.inera.ifv.hsawsresponder.v3.SpecialityNamesType;
import se.inera.privatlakarportal.hsa.services.HospPersonService;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.PrivatlakareId;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareIdRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.dto.HospInformation;
import se.inera.privatlakarportal.service.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationRequest;
import se.inera.privatlakarportal.web.controller.api.dto.Registration;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceImplTest {

    @Mock
    private PrivatlakareRepository privatlakareRepository;

    @Mock
    private PrivatlakareIdRepository privatlakareidRepository;

    @Mock
    private HospPersonService hospPersonService;

    @InjectMocks
    private RegisterService registerService = new RegisterServiceImpl();

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
    public void testCreateRegistration() {

        PrivatlakareId privatlakareId = new PrivatlakareId();
        privatlakareId.setId(1);
        when(privatlakareidRepository.save(any(PrivatlakareId.class))).thenReturn(privatlakareId);

        Registration registration = createValidRegistration();
        registerService.createRegistration(registration);

        verify(privatlakareRepository).save(any(Privatlakare.class));
    }

    @Test(expected = PrivatlakarportalServiceException.class)
    public void testInvalidCreateRegistration() {
        Registration registration = new Registration();
        registerService.createRegistration(registration);
    }

    @Test
    public void getHospInformation() {
        GetHospPersonResponseType hospPersonResponse = new GetHospPersonResponseType();
        hospPersonResponse.setPersonalIdentityNumber("191212-1212");
        hospPersonResponse.setPersonalPrescriptionCode("0000000");
        HsaTitlesType hasTitles = new HsaTitlesType();
        hasTitles.getHsaTitle().add("Test title");
        hospPersonResponse.setHsaTitles(hasTitles);
        SpecialityNamesType specialityNamesType = new SpecialityNamesType();
        specialityNamesType.getSpecialityName().add("Test speciality");
        hospPersonResponse.setSpecialityNames(specialityNamesType);

        when(hospPersonService.getHospPerson(any(String.class))).thenReturn(hospPersonResponse);

        HospInformation hospInformation = registerService.getHospInformation();

        assert(hospInformation.getPersonalPrescriptionCode() == "0000000");
        assert(hospInformation.getHsaTitles().size() == 1);
        assert(hospInformation.getHsaTitles().get(0) == "Test title");
        assert(hospInformation.getSpecialityNames().size() == 1);
        assert(hospInformation.getSpecialityNames().get(0) == "Test speciality");
    }
}
