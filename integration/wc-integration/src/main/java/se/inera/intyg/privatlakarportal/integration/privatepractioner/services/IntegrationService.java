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
package se.inera.intyg.privatlakarportal.integration.privatepractioner.services;

import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionerresponder.v1.GetPrivatePractitionerResponseType;
import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitionerresponder.v1.ValidatePrivatePractitionerResponseType;

/**
 * Created by pebe on 2015-08-17.
 */
public interface IntegrationService {
    GetPrivatePractitionerResponseType getPrivatePractitionerByHsaId(String personHsaId);

    GetPrivatePractitionerResponseType getPrivatePractitionerByPersonId(String personalIdentityNumber);

    ValidatePrivatePractitionerResponseType validatePrivatePractitionerByHsaId(String personHsaId);

    ValidatePrivatePractitionerResponseType validatePrivatePractitionerByPersonId(String personalIdentityNumber);
}
