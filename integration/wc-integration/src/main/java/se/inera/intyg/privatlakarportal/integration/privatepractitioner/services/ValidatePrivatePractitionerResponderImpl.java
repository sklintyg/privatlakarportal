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
package se.inera.intyg.privatlakarportal.integration.privatepractitioner.services;

// CHECKSTYLE:OFF LineLength

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitioner.v1.rivtabp21.ValidatePrivatePractitionerResponderInterface;
import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitionerresponder.v1.ValidatePrivatePractitionerResponseType;
import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitionerresponder.v1.ValidatePrivatePractitionerType;

// CHECKSTYLE:ON LineLength

/**
 * Created by pebe on 2015-08-17.
 */
public class ValidatePrivatePractitionerResponderImpl implements ValidatePrivatePractitionerResponderInterface {

    @Autowired
    private se.inera.intyg.privatlakarportal.integration.privatepractitioner.services.IntegrationService integrationService;

    @Override
    public ValidatePrivatePractitionerResponseType validatePrivatePractitioner(String s,
        ValidatePrivatePractitionerType validatePrivatePractitionerType) {

        final boolean hasHsaArgument = !StringUtils.isEmpty(validatePrivatePractitionerType.getPersonHsaId());
        final boolean hasPersonArgument = !StringUtils.isEmpty(validatePrivatePractitionerType.getPersonalIdentityNumber());

        if (hasHsaArgument && hasPersonArgument) {
            throw new IllegalArgumentException("Endast ett av argumenten hsaIdentityNumber och personalIdentityNumber får vara satt.");
        } else if (hasHsaArgument) {
            return integrationService.validatePrivatePractitionerByHsaId(validatePrivatePractitionerType.getPersonHsaId());
        } else if (hasPersonArgument) {
            return integrationService.validatePrivatePractitionerByPersonId(validatePrivatePractitionerType.getPersonalIdentityNumber());
        } else {
            throw new IllegalArgumentException(
                "Inget av argumenten hsaIdentityNumber och personalIdentityNumber är satt. Ett av dem måste ha ett värde.");
        }
    }
}
