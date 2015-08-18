package se.inera.privatlakarportal.integration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import se.inera.privatlakarportal.common.integration.json.CustomObjectMapper;
import se.inera.privatlakarportal.persistence.model.*;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionerresponder.v1.GetPrivatePractitionerResponseType;
import se.riv.infrastructure.directory.privatepractitioner.v1.HoSPersonType;
import se.riv.infrastructure.directory.privatepractitioner.v1.ResultCodeEnum;
import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitionerresponder.v1.ValidatePrivatePractitionerResponseType;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by pebe on 2015-08-18.
 */
@RunWith(MockitoJUnitRunner.class)
public class IntegrationServiceTest {

    @Mock
    private PrivatlakareRepository privatlakareRepository;

    @InjectMocks
    private IntegrationServiceImpl integrationService;

    private static final String EJ_GODKAND_HSA_ID = "nonExistingId";
    private static final String EJ_GODKAND_PERSON_ID = "nonExistingId";
    private static final String GODKAND_HSA_ID = "existingHsaId";
    private static final String GODKAND_PERSON_ID = "existingPersonId";
    private static final String FINNS_EJ_HSA_ID = "nonExistingHsaId";
    private static final String FINNS_EJ_PERSON_ID = "nonExistingPersonId";

    private HoSPersonType verifyHosPerson;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new CustomObjectMapper();

        Resource res = new ClassPathResource("test_Privatlakare.json");
        Privatlakare privatlakare = objectMapper.readValue(res.getInputStream(), Privatlakare.class);

        Privatlakare privatlakare_ej_godkand = objectMapper.readValue(res.getInputStream(), Privatlakare.class);
        privatlakare_ej_godkand.setGodkandAnvandare(false);

        res = new ClassPathResource("test_HosPerson.json");
        verifyHosPerson = objectMapper.readValue(res.getInputStream(), HoSPersonType.class);

        when(privatlakareRepository.findByHsaId(GODKAND_HSA_ID)).thenReturn(privatlakare);
        when(privatlakareRepository.findByPersonId(GODKAND_PERSON_ID)).thenReturn(privatlakare);
        when(privatlakareRepository.findByHsaId(FINNS_EJ_HSA_ID)).thenReturn(null);
        when(privatlakareRepository.findByPersonId(FINNS_EJ_PERSON_ID)).thenReturn(null);
        when(privatlakareRepository.findByHsaId(EJ_GODKAND_HSA_ID)).thenReturn(privatlakare_ej_godkand);
        when(privatlakareRepository.findByPersonId(EJ_GODKAND_PERSON_ID)).thenReturn(privatlakare_ej_godkand);
    }

    @Test
    public void testGetPrivatePractitionerByHsaId() {
        GetPrivatePractitionerResponseType response = integrationService.getPrivatePractitionerByHsaId(GODKAND_HSA_ID);
        assertEquals(ResultCodeEnum.OK, response.getResultCode());
        assertNotNull(response.getHoSPerson());
        ReflectionAssert.assertReflectionEquals(verifyHosPerson, response.getHoSPerson(), ReflectionComparatorMode.LENIENT_ORDER);
    }

    @Test
    public void testGetPrivatePractitionerByPersonId() {
        GetPrivatePractitionerResponseType response = integrationService.getPrivatePractitionerByPersonId(GODKAND_PERSON_ID);
        assertEquals(ResultCodeEnum.OK, response.getResultCode());
        assertNotNull(response.getHoSPerson());
        ReflectionAssert.assertReflectionEquals(verifyHosPerson, response.getHoSPerson(), ReflectionComparatorMode.LENIENT_ORDER);
    }

    @Test
    public void testGetPrivatePractitionerByHsaIdNonExisting() {
        GetPrivatePractitionerResponseType response = integrationService.getPrivatePractitionerByHsaId(FINNS_EJ_HSA_ID);
        assertEquals(ResultCodeEnum.ERROR, response.getResultCode());
        assertNull(response.getHoSPerson());
    }

    @Test
    public void testGetPrivatePractitionerByPersonIdNonExisting() {
        GetPrivatePractitionerResponseType response = integrationService.getPrivatePractitionerByPersonId(FINNS_EJ_PERSON_ID);
        assertEquals(ResultCodeEnum.ERROR, response.getResultCode());
        assertNull(response.getHoSPerson());
    }

    @Test
    public void testValidatePrivatePractitionerByHsaId() {
        ValidatePrivatePractitionerResponseType response = integrationService.validatePrivatePractitionerByHsaId(GODKAND_HSA_ID);
        assertEquals(ResultCodeEnum.OK, response.getResultCode());
    }

    @Test
    public void testValidatePrivatePractitionerByPersonId() {
        ValidatePrivatePractitionerResponseType response = integrationService.validatePrivatePractitionerByPersonId(GODKAND_PERSON_ID);
        assertEquals(ResultCodeEnum.OK, response.getResultCode());
    }

    @Test
    public void testValidatePrivatePractitionerByHsaIdEjGodkand() {
        ValidatePrivatePractitionerResponseType response = integrationService.validatePrivatePractitionerByHsaId(EJ_GODKAND_HSA_ID);
        assertEquals(ResultCodeEnum.ERROR, response.getResultCode());
    }

    @Test
    public void testValidatePrivatePractitionerByPersonIdEjGodkänd() {
        ValidatePrivatePractitionerResponseType response = integrationService.validatePrivatePractitionerByPersonId(EJ_GODKAND_PERSON_ID);
        assertEquals(ResultCodeEnum.ERROR, response.getResultCode());
    }

    @Test
    public void testValidatePrivatePractitionerByHsaIdNonExisting() {
        ValidatePrivatePractitionerResponseType response = integrationService.validatePrivatePractitionerByHsaId(FINNS_EJ_HSA_ID);
        assertEquals(ResultCodeEnum.ERROR, response.getResultCode());
    }

    @Test
    public void testValidatePrivatePractitionerByPersonIdNonExisting() {
        ValidatePrivatePractitionerResponseType response = integrationService.validatePrivatePractitionerByHsaId(FINNS_EJ_PERSON_ID);
        assertEquals(ResultCodeEnum.ERROR, response.getResultCode());
    }

    private void verifyPrivatlakare(HoSPersonType hoSPersonType, Privatlakare privatlakare) {
        assertEquals(privatlakare.getBefattningar().size(), hoSPersonType.getBefattning().size());
        //assertEquals(hoSPersonType.getBefattning(), privatlakare.getBefattningar());
        //assertEquals(hoSPersonType.getEnhet(), privatlakare.get());
        assertEquals(privatlakare.getForskrivarKod(), hoSPersonType.getForskrivarkod());
        assertEquals(privatlakare.getFullstandigtNamn(), hoSPersonType.getFullstandigtNamn());
        assertEquals(privatlakare.getHsaId(), hoSPersonType.getHsaId().getExtension());
        assertEquals(privatlakare.getLegitimeradeYrkesgrupper().size(), hoSPersonType.getLegitimeradYrkesgrupp().size());
        //assertEquals(hoSPersonType.getLegitimeradYrkesgrupp(), privatlakare.getLegitimeradeYrkesgrupper());
        assertEquals(privatlakare.getPersonId(), hoSPersonType.getPersonId().getExtension());
        assertEquals(privatlakare.getSpecialiteter().size(), hoSPersonType.getSpecialitet().size());
        for (Specialitet specialitet : privatlakare.getSpecialiteter()) {
//            assertThat("Specialitet matches", );
        }
        //assertEquals(hoSPersonType.getSpecialitet(), privatlakare.getSpecialiteter());
//        assertThat(hoSPersonType.getSpecialitet(), IsIterableContainingInAnyOrder());
    }

}
