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
package se.inera.privatlakarportal.auth;

import java.io.Serializable;

/**
 * Created by pebe on 2015-08-11.
 */
public class PrivatlakarUser implements Serializable {

    private static final long serialVersionUID = 8711015219408194075L;

    private String personalIdentityNumber;
    private String name;
    private String authenticationScheme;
    private boolean nameFromPuService;

    public PrivatlakarUser(String personalIdentityNumber, String name) {
        this.personalIdentityNumber = personalIdentityNumber;
        this.name = name;
        nameFromPuService = false;
    }

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public String getName() {
        return name;
    }

    public String getAuthenticationScheme() {
        return authenticationScheme;
    }

    public void setAuthenticationScheme(String authenticationScheme) {
        this.authenticationScheme = authenticationScheme;
    }

    public boolean isNameFromPuService() {
        return nameFromPuService;
    }

    public void updateNameFromPuService(String name) {
        this.name = name;
        nameFromPuService = true;
    }

}
