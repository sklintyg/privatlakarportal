/*
 * Copyright (C) 2017 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.integration.privatepractioner.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitionerresponder.v1.ValidatePrivatePractitionerType;

/**
 * Created by pebe on 2015-08-19.
 */
@RunWith(MockitoJUnitRunner.class)
public class ValidatePrivatePractitionerResponderTest {

    @Mock
    private IntegrationService integrationService;

    @InjectMocks
    private ValidatePrivatePractitionerResponderImpl validatePrivatePractitionerResponder = new ValidatePrivatePractitionerResponderImpl();

    @Test(expected = IllegalArgumentException.class)
    public void testGetBothIds() {
        ValidatePrivatePractitionerType validatePrivatePractitionerType = new ValidatePrivatePractitionerType();
        validatePrivatePractitionerType.setPersonalIdentityNumber("id1");
        validatePrivatePractitionerType.setPersonHsaId("id2");
        validatePrivatePractitionerResponder.validatePrivatePractitioner("", validatePrivatePractitionerType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNoIds() {
        ValidatePrivatePractitionerType validatePrivatePractitionerType = new ValidatePrivatePractitionerType();
        validatePrivatePractitionerType.setPersonalIdentityNumber(null);
        validatePrivatePractitionerType.setPersonHsaId(null);
        validatePrivatePractitionerResponder.validatePrivatePractitioner("", validatePrivatePractitionerType);
    }

    @Test
    public void testGetPersonId() {
        ValidatePrivatePractitionerType validatePrivatePractitionerType = new ValidatePrivatePractitionerType();
        validatePrivatePractitionerType.setPersonalIdentityNumber("id1");
        validatePrivatePractitionerType.setPersonHsaId(null);
        validatePrivatePractitionerResponder.validatePrivatePractitioner("", validatePrivatePractitionerType);
        verify(integrationService).validatePrivatePractitionerByPersonId("id1");
        verifyNoMoreInteractions(integrationService);
    }

    @Test
    public void testGetHsaId() {
        ValidatePrivatePractitionerType validatePrivatePractitionerType = new ValidatePrivatePractitionerType();
        validatePrivatePractitionerType.setPersonalIdentityNumber(null);
        validatePrivatePractitionerType.setPersonHsaId("id2");
        validatePrivatePractitionerResponder.validatePrivatePractitioner("", validatePrivatePractitionerType);
        verify(integrationService).validatePrivatePractitionerByHsaId("id2");
        verifyNoMoreInteractions(integrationService);
    }
}
