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
package se.inera.privatlakarportal.common.service;

import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.persistence.model.Privatlakare;

public interface MailService {

    /**
     * sendRegistrationStatusEmail is used for notifying users about the pending status of their registration via the
     * specified email address.
     *
     * @param status
     *            {@link RegistrationStatus} Used to determine what email to send.
     * @param registration
     *            @{link Privatlakare} object with details such as email address.
     * @throws PrivatlakarportalServiceException
     *             @{link PrivatlakarportalServiceException}
     */
    void sendRegistrationStatusEmail(RegistrationStatus status, Privatlakare privatlakare);

    void sendHsaGenerationStatusEmail();
}
