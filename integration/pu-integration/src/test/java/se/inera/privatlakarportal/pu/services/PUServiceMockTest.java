package se.inera.privatlakarportal.pu.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import se.inera.privatlakarportal.pu.model.PersonSvar;
import se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v1.LookupResidentForFullProfileResponseType;
import se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v11.LookupResidentForFullProfileResponderInterface;
import se.riv.population.residentmaster.types.v1.*;

@RunWith(MockitoJUnitRunner.class)
public class PUServiceMockTest {

    @Mock
    private LookupResidentForFullProfileResponderInterface puWeb;

    @InjectMocks
    private PUServiceImpl puService;

    @Test
    public void testNoAddress() {
        when(puWeb.lookupResidentForFullProfile(anyString(), any())).thenReturn(createResponse());
        PersonSvar result = puService.getPerson("personId");
        assertNull(result.getPerson().getPostadress());
        assertNull(result.getPerson().getPostnummer());
        assertNull(result.getPerson().getPostort());
    }

    private LookupResidentForFullProfileResponseType createResponse() {
        LookupResidentForFullProfileResponseType res = new LookupResidentForFullProfileResponseType();
        ResidentType resident = new ResidentType();
        PersonpostTYPE personpost = new PersonpostTYPE();
        personpost.setFolkbokforingsadress(null);
        personpost.setNamn(new NamnTYPE());
        resident.setPersonpost(personpost);
        res.getResident().add(resident);
        return res;
    }
}
