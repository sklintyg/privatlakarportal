/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.hsa.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonType;
import se.inera.ifv.hsawsresponder.v3.HandleCertifierResponseType;
import se.inera.ifv.hsawsresponder.v3.HandleCertifierType;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;

@RunWith(MockitoJUnitRunner.class)
public class HospPersonServiceTest {

    private static final String VALID_PERSON_ID = "1912121212";
    private static final String INVALID_PERSON_ID = "0000000000";
    private static final String CERTIFIER_ID = "CERTIFIER_0001";

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


    @Test
    public void testAddToCertifier() {
        HandleCertifierResponseType response = new HandleCertifierResponseType();
        response.setResult("OK");
        when(hsaWebServiceCalls.callHandleCertifier(any(HandleCertifierType.class))).thenReturn(response);

        hospPersonService.addToCertifier(VALID_PERSON_ID, CERTIFIER_ID);

        HandleCertifierType parameters = new HandleCertifierType();
        parameters.setAddToCertifiers(true);
        parameters.setCertifierId(CERTIFIER_ID);
        parameters.setPersonalIdentityNumber(VALID_PERSON_ID);
        verify(hsaWebServiceCalls).callHandleCertifier(parameters);
    }

    @Test
    public void testRemoveFromCertifier() {

        HandleCertifierResponseType response = new HandleCertifierResponseType();
        response.setResult("OK");
        when(hsaWebServiceCalls.callHandleCertifier(any(HandleCertifierType.class))).thenReturn(response);

        hospPersonService.removeFromCertifier(VALID_PERSON_ID, CERTIFIER_ID, "Test");

        HandleCertifierType parameters = new HandleCertifierType();
        parameters.setAddToCertifiers(false);
        parameters.setCertifierId(CERTIFIER_ID);
        parameters.setPersonalIdentityNumber(VALID_PERSON_ID);
        parameters.setReason("Test");
        verify(hsaWebServiceCalls).callHandleCertifier(parameters);
    }

}
