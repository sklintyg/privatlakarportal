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
package se.inera.intyg.privatlakarportal.service;

import se.inera.intyg.privatlakarportal.common.model.Registration;
import se.inera.intyg.privatlakarportal.common.model.RegistrationStatus;
import se.inera.intyg.privatlakarportal.service.model.*;

/**
 * Created by pebe on 2015-06-25.
 */
public interface RegisterService {

    RegistrationStatus createRegistration(Registration registration, Long godkantMedgivandeVersion);

    SaveRegistrationResponseStatus saveRegistration(Registration registration);

    HospInformation getHospInformation();

    RegistrationWithHospInformation getRegistration();

    boolean removePrivatlakare(String personId);

    void injectHsaInterval(int hsaIdNotificationInterval);
}