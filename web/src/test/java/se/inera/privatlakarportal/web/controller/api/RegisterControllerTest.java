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
package se.inera.privatlakarportal.web.controller.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.inera.privatlakarportal.service.RegisterService;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationRequest;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {
    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController = new RegisterController();

    @Test
    public void testCreateRegistration() {
        CreateRegistrationRequest request = new CreateRegistrationRequest();
        request.setGodkantMedgivandeVersion(1L);
        registerController.createRegistration(request);

        verify(registerService).createRegistration(request.getRegistration(), 1L);
    }
}
