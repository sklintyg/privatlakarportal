/*
 * Copyright (C) 2022 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.service.model;

import java.time.LocalDateTime;

public class PrivatePractitioner {

    private String hsaId;
    private String name;
    private String careproviderName;
    private String email;
    private LocalDateTime registrationDate;

    public PrivatePractitioner(String hsaId, String name, String careproviderName, String email, LocalDateTime registrationDate) {
        this.hsaId = hsaId;
        this.name = name;
        this.careproviderName = careproviderName;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public String getHsaId() {
        return hsaId;
    }

    public String getName() {
        return name;
    }

    public String getCareproviderName() {
        return careproviderName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
}
