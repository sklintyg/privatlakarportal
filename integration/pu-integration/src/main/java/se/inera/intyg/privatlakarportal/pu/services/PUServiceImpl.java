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
package se.inera.intyg.privatlakarportal.pu.services;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.inera.intyg.privatlakarportal.pu.model.Person;
import se.inera.intyg.privatlakarportal.pu.model.PersonSvar;
import se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v1.LookUpSpecificationType;
import se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v1.LookupResidentForFullProfileResponseType;
import se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v1.LookupResidentForFullProfileType;
import se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v11.LookupResidentForFullProfileResponderInterface;
import se.riv.population.residentmaster.types.v1.JaNejTYPE;
import se.riv.population.residentmaster.types.v1.NamnTYPE;
import se.riv.population.residentmaster.types.v1.ResidentType;
import se.riv.population.residentmaster.types.v1.SvenskAdressTYPE;

import javax.xml.ws.soap.SOAPFaultException;

@Service
public class PUServiceImpl implements PUService {

    private static final Logger LOG = LoggerFactory.getLogger(PUServiceImpl.class);

    @Autowired
    private LookupResidentForFullProfileResponderInterface puWebServiceClient;

    @Value("${putjanst.logicaladdress}")
    private String logicaladdress;

    // CHECKSTYLE:OFF LineLength
    @Override
    @Cacheable(value = "personCache", key = "#personId", unless = "#result.status == T(se.inera.intyg.privatlakarportal.pu.model.PersonSvar$Status).ERROR")
    public PersonSvar getPerson(String personId) {
        // CHECKSTYLE:ON LineLength
        String normalizedId = normalizeId(personId);

        LOG.debug("Looking up person '{}'({})", normalizedId, personId);
        LookupResidentForFullProfileType parameters = new LookupResidentForFullProfileType();
        parameters.setLookUpSpecification(new LookUpSpecificationType());
        parameters.getPersonId().add(normalizedId);
        try {
            LookupResidentForFullProfileResponseType response = puWebServiceClient.lookupResidentForFullProfile(logicaladdress, parameters);
            if (response.getResident().isEmpty()) {
                LOG.warn("No person '{}'({}) found", normalizedId, personId);
                return new PersonSvar(null, PersonSvar.Status.NOT_FOUND);
            }

            ResidentType resident = response.getResident().get(0);

            NamnTYPE namn = resident.getPersonpost().getNamn();

            SvenskAdressTYPE adress = resident.getPersonpost().getFolkbokforingsadress();

            String adressRader = buildAdress(adress);
            Person person = new Person(personId, resident.getSekretessmarkering() == JaNejTYPE.J, namn.getFornamn(),
                    namn.getMellannamn(), namn.getEfternamn(), adressRader, adress != null ? adress.getPostNr() : null,
                    adress != null ? adress.getPostort() : null);
            LOG.debug("Person '{}' found", normalizedId);

            return new PersonSvar(person, PersonSvar.Status.FOUND);
        } catch (SOAPFaultException e) {
            LOG.warn("Error occured, no person '{}'({}) found", normalizedId, personId);
            return new PersonSvar(null, PersonSvar.Status.ERROR);
        }
    }

    @Override
    @VisibleForTesting
    @CacheEvict(value = "personCache", allEntries = true)
    public void clearCache() {
        LOG.debug("personCache cleared");
    }

    private String normalizeId(String personId) {
        // CHECKSTYLE:OFF MagicNumber
        if (personId.length() == 13) {
            return personId.substring(0, 8) + personId.substring(9);
        } else {
            return personId;
        }
        // CHECKSTYLE:ON MagicNumber
    }

    private String buildAdress(SvenskAdressTYPE adress) {
        return adress == null ? null
                : joinIgnoreNulls(", ", adress.getCareOf(), adress.getUtdelningsadress1(), adress.getUtdelningsadress2());
    }

    private String joinIgnoreNulls(String separator, String... values) {
        StringBuilder builder = new StringBuilder();
        for (String value : values) {
            if (value != null) {
                if (builder.length() > 0) {
                    builder.append(separator);
                }
                builder.append(value);
            }
        }
        return builder.toString();
    }
}
