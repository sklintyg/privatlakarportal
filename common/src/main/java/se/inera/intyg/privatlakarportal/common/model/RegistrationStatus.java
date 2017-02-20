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
package se.inera.privatlakarportal.common.model;

/**
 * Created by pebe on 2015-08-25.
 */
public enum RegistrationStatus {
    NOT_STARTED,

    // Om användaren inte fanns i HSA:s HOSP visar systemet information om att verifiering har påbörats. HSA lagrar användarens personnummer så att information om denne inkluderas nästa gång information hämtas från HOSP. Användningsfallet avslutas.
    WAITING_FOR_HOSP,

    // Om läkaren fanns i HSA:s HOSP men inte har en giltig läkarlegitimation visar systemet information om att denne inte är behörig.
    NOT_AUTHORIZED,

    // Om användaren fanns i HSA:s HOSP och har en giltig läkarlegitimation godkänner systemet användaren (HoS-personal.godkänd sätts till ”True”) och systemet visar info om att denne kan börja använda systemet.
    AUTHORIZED
}
