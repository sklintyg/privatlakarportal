package se.inera.privatlakarportal.hsa.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.xml.ws.WebServiceException;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.HsaTitlesType;
import se.inera.ifv.hsawsresponder.v3.SpecialityCodesType;
import se.inera.ifv.hsawsresponder.v3.SpecialityNamesType;
import se.inera.ifv.hsawsresponder.v3.TitleCodesType;
import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.common.service.MailService;
import se.inera.privatlakarportal.persistence.model.HospUppdatering;
import se.inera.privatlakarportal.persistence.model.LegitimeradYrkesgrupp;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.Specialitet;
import se.inera.privatlakarportal.persistence.repository.HospUppdateringRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;

/**
 * Created by pebe on 2015-09-04.
 */
@RunWith(MockitoJUnitRunner.class)
public class HospUpdateServiceImplTest {

    private final String PERSON_ID = "1912121212";
    private final String PERSONAL_PRESCRIPTION_CODE = "7654321";

    @Mock
    private HospPersonService hospPersonService;

    @Mock
    private PrivatlakareRepository privatlakareRepository;

    @Mock
    private HospUppdateringRepository hospUppdateringRepository;

    @Mock
    private MailService mailService;

    @InjectMocks
    private HospUpdateService hospUpdateService = new HospUpdateServiceImpl();

    @Test
    public void testUpdateHospInformation() {

        HospUppdatering hospUppdatering = new HospUppdatering();
        hospUppdatering.setSenasteHospUppdatering(LocalDateTime.parse("2015-09-01"));
        when(hospUppdateringRepository.findSingle()).thenReturn(hospUppdatering);
        LocalDateTime hospLastUpdate = LocalDateTime.parse("2015-09-05");
        when(hospPersonService.getHospLastUpdate()).thenReturn(hospLastUpdate);
        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);
        ArrayList list = new ArrayList();
        list.add(privatlakare);
        when(privatlakareRepository.findWithoutLakarBehorighet()).thenReturn(list);

        GetHospPersonResponseType hospPersonResponse = createGetHospPersonResponse();
        hospPersonResponse.getTitleCodes().getTitleCode().add("DT");
        hospPersonResponse.getHsaTitles().getHsaTitle().add("Dietist");
        hospPersonResponse.getSpecialityCodes().getSpecialityCode().add("12");
        hospPersonResponse.getSpecialityNames().getSpecialityName().add("Specialitet");
        when(hospPersonService.getHospPerson(PERSON_ID)).thenReturn(hospPersonResponse);

        hospUpdateService.updateHospInformation();

        // sensateHospUppdatering in DB should be se to hospLastUpdate from HSA
        assertEquals(hospLastUpdate, hospUppdatering.getSenasteHospUppdatering());
        verify(hospUppdateringRepository).save(hospUppdatering);

        // privatlakare should be updated with new hospinformation
        verify(privatlakareRepository).save(privatlakare);
    }

    @Test
    public void testUpdateHospInformationKanEjKontaktaHSA1() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);

        when(hospPersonService.getHospPerson(PERSON_ID)).thenThrow(new WebServiceException("Could not send message"));

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare, true);

        verify(hospPersonService).getHospPerson(PERSON_ID);
        verifyNoMoreInteractions(hospPersonService);
        assertEquals(response, RegistrationStatus.WAITING_FOR_HOSP);
    }

    @Test
    public void testUpdateHospInformationKanEjKontaktaHSA2() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);

        when(hospPersonService.getHospPerson(PERSON_ID)).thenReturn(null);

        when(hospPersonService.handleCertifier(eq(PERSON_ID), any(String.class))).thenThrow(new WebServiceException("Could not send message"));

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare, true);

        verify(hospPersonService).getHospPerson(PERSON_ID);
        verify(hospPersonService).handleCertifier(eq(PERSON_ID), any(String.class));
        verifyNoMoreInteractions(hospPersonService);
        assertEquals(response, RegistrationStatus.WAITING_FOR_HOSP);
    }

    @Test
    public void testUpdateHospInformationEjLakare() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);

        when(hospPersonService.getHospPerson(PERSON_ID)).thenReturn(createGetHospPersonResponse());

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare, true);

        verify(hospPersonService, times(0)).handleCertifier(any(String.class), any(String.class));
        assertEquals(response, RegistrationStatus.NOT_AUTHORIZED);
    }

    @Test
    public void testUpdateHospInformationEjIHosp() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);

        when(hospPersonService.getHospPerson(PERSON_ID)).thenReturn(null);

        when(hospPersonService.handleCertifier(eq(PERSON_ID), any(String.class))).thenReturn(true);

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare, true);

        verify(hospPersonService).handleCertifier(eq(PERSON_ID), any(String.class));
        assertEquals(response, RegistrationStatus.WAITING_FOR_HOSP);
    }

    @Test
    public void testUpdateHospInformationLakare() {

        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);

        GetHospPersonResponseType hospPersonResponse = createGetHospPersonResponse();
        hospPersonResponse.getTitleCodes().getTitleCode().add("LK");
        hospPersonResponse.getHsaTitles().getHsaTitle().add("Läkare");
        when(hospPersonService.getHospPerson(PERSON_ID)).thenReturn(hospPersonResponse);

        RegistrationStatus response = hospUpdateService.updateHospInformation(privatlakare, true);

        verify(hospPersonService, times(0)).handleCertifier(any(String.class), any(String.class));
        assertEquals(response, RegistrationStatus.AUTHORIZED);
    }

    @Test
    public void testCheckForUpdatedHospInformationNotUpdated() {
        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);
        privatlakare.setSenasteHospUppdatering(LocalDateTime.parse("2015-09-01"));

        when(hospPersonService.getHospLastUpdate()).thenReturn(LocalDateTime.parse("2015-09-01"));

        hospUpdateService.checkForUpdatedHospInformation(privatlakare);

        verify(hospPersonService).getHospLastUpdate();
        verifyNoMoreInteractions(hospPersonService);
        verifyNoMoreInteractions(privatlakareRepository);
    }

    @Test
    public void testCheckForUpdatedHospInformationUpdated() {
        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);
        privatlakare.setSenasteHospUppdatering(LocalDateTime.parse("2015-09-01"));

        when(hospPersonService.getHospLastUpdate()).thenReturn(LocalDateTime.parse("2015-09-05"));

        GetHospPersonResponseType hospPersonResponse = createGetHospPersonResponse();
        hospPersonResponse.getTitleCodes().getTitleCode().add("DT");
        hospPersonResponse.getHsaTitles().getHsaTitle().add("Dietist");
        hospPersonResponse.getSpecialityCodes().getSpecialityCode().add("12");
        hospPersonResponse.getSpecialityNames().getSpecialityName().add("Specialitet");
        when(hospPersonService.getHospPerson(PERSON_ID)).thenReturn(hospPersonResponse);

        hospUpdateService.checkForUpdatedHospInformation(privatlakare);

        verify(hospPersonService).getHospLastUpdate();
        verify(hospPersonService).getHospPerson(PERSON_ID);
        verifyNoMoreInteractions(hospPersonService);
        verify(privatlakareRepository).save(privatlakare);

        assertEquals(1, privatlakare.getLegitimeradeYrkesgrupper().size());
        LegitimeradYrkesgrupp l = privatlakare.getLegitimeradeYrkesgrupper().iterator().next();
        assertEquals("DT", l.getKod());
        assertEquals("Dietist", l.getNamn());
        Specialitet s = privatlakare.getSpecialiteter().iterator().next();
        assertEquals("12", s.getKod());
        assertEquals("Specialitet", s.getNamn());
        assertEquals(PERSONAL_PRESCRIPTION_CODE, privatlakare.getForskrivarKod());
    }

    @Test
    public void testCheckForUpdatedHospInformationKanEjKontaktaHSA1() {
        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);
        privatlakare.setSenasteHospUppdatering(LocalDateTime.parse("2015-09-01"));

        when(hospPersonService.getHospLastUpdate()).thenThrow(new WebServiceException("Could not send message"));
        hospUpdateService.checkForUpdatedHospInformation(privatlakare);
    }

    @Test
    public void testCheckForUpdatedHospInformationKanEjKontaktaHSA2() {
        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPersonId(PERSON_ID);
        privatlakare.setSenasteHospUppdatering(LocalDateTime.parse("2015-09-01"));

        when(hospPersonService.getHospLastUpdate()).thenReturn(LocalDateTime.parse("2015-09-05"));

        when(hospPersonService.getHospPerson(PERSON_ID)).thenThrow(new WebServiceException("Could not send message"));

        hospUpdateService.checkForUpdatedHospInformation(privatlakare);
    }

    private GetHospPersonResponseType createGetHospPersonResponse() {
        GetHospPersonResponseType getHospPersonResponseType = new GetHospPersonResponseType();

        getHospPersonResponseType.setSpecialityCodes(new SpecialityCodesType());
        getHospPersonResponseType.setSpecialityNames(new SpecialityNamesType());
        getHospPersonResponseType.setTitleCodes(new TitleCodesType());
        getHospPersonResponseType.setHsaTitles(new HsaTitlesType());
        getHospPersonResponseType.setPersonalPrescriptionCode(PERSONAL_PRESCRIPTION_CODE);

        return getHospPersonResponseType;
    }
}
