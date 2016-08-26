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
package se.inera.privatlakarportal.hsa.stub;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HsaServiceStub {

    private Map<String, HsaHospPerson> personMap = new HashMap<String, HsaHospPerson>();

    private LocalDateTime hospLastUpdate;

    public HsaHospPerson getHospPerson(String personId) {
        return personMap.get(personId);
    }

    public void addHospPerson(HsaHospPerson hospPerson) {
        personMap.put(hospPerson.getPersonalIdentityNumber(), hospPerson);
        hospLastUpdate = new LocalDateTime();
    }

    public void removeHospPerson(String id) {
        personMap.remove(id);
        hospLastUpdate = new LocalDateTime();
    }

    public LocalDateTime getHospLastUpdate() {
        return hospLastUpdate;
    }
}
