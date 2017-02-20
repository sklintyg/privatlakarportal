/**
 * Copyright (C) 2016 Inera AB (http://www.inera.se)
 *
 * This file is part of privatlakarportal (https://github.com/sklintyg/privatlakarportal).
 *
 * privatlakarportal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * privatlakarportal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
