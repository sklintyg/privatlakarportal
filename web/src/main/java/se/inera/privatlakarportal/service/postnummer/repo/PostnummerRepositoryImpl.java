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
package se.inera.privatlakarportal.service.postnummer.repo;

import org.springframework.stereotype.Component;
import se.inera.privatlakarportal.service.postnummer.model.Omrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pebe on 2015-08-12.
 */
@Component
public class PostnummerRepositoryImpl implements PostnummerRepository {

    private Map<String, List<Omrade>> postnummerRepository = new HashMap<String, List<Omrade>>();

    @Override
    public List<Omrade> getOmradeByPostnummer(String postnummer) {
        return postnummerRepository.get(postnummer);
    }

    @Override
    public int nbrOfPostnummer() {
        return postnummerRepository.size();
    }

    void addPostnummer(String postnummer, Omrade omrade) {
        if (postnummerRepository.containsKey(postnummer)) {
            postnummerRepository.get(postnummer).add(omrade);
        } else {
            List<Omrade> omradeList = new ArrayList<Omrade>();
            omradeList.add(omrade);
            postnummerRepository.put(postnummer, omradeList);
        }
    }
}
