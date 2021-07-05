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

import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitioner.v1.rivtabp21.ValidatePrivatePractitionerResponderInterface;
import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitionerresponder.v1.ValidatePrivatePractitionerResponseType;
import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitionerresponder.v1.ValidatePrivatePractitionerType;

// CHECKSTYLE:ON LineLength

/**
 * Created by pebe on 2015-08-17.
 */
public class ValidatePrivatePractitionerResponderImpl implements ValidatePrivatePractitionerResponderInterface {

    @Override
    public ValidatePrivatePractitionerResponseType validatePrivatePractitioner(String s,
        ValidatePrivatePractitionerType validatePrivatePractitionerType) {

        throw new UnsupportedOperationException("Method has been replaced by '/validate' API in PrivatePractitionerController.");
    }

}
