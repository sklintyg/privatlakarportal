/*
 * Copyright (C) 2017 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.hsa.services;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.inera.ifv.hsawsresponder.v3.GetHospLastUpdateResponseType;
import se.inera.ifv.hsawsresponder.v3.GetHospLastUpdateType;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonType;
import se.inera.ifv.hsawsresponder.v3.HandleCertifierResponseType;
import se.inera.ifv.hsawsresponder.v3.HandleCertifierType;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;

@Service
public class HospPersonServiceImpl implements HospPersonService {

    private static final Logger LOG = LoggerFactory.getLogger(HospPersonServiceImpl.class);

    @Autowired
    private HSAWebServiceCalls client;

    @Override
    public GetHospPersonResponseType getHospPerson(String personId) {

        LOG.debug("Getting hospPerson from HSA for '{}'", personId);

        GetHospPersonType parameters = new GetHospPersonType();
        parameters.setPersonalIdentityNumber(personId);

        GetHospPersonResponseType response = client.callGetHospPerson(parameters);

        if (response == null) {
            LOG.debug("Response did not contain any hospPerson for '{}'", personId);
            return null;
        }

        return response;
    }

    @Override
    public LocalDateTime getHospLastUpdate() {

        LOG.debug("Calling getHospLastUpdate");

        GetHospLastUpdateType parameters = new GetHospLastUpdateType();

        GetHospLastUpdateResponseType response = client.callGetHospLastUpdate(parameters);

        return response.getLastUpdate();
    }

    @Override
    public boolean addToCertifier(String personId, String certifierId) {
        return handleCertifier(true, personId, certifierId, null);
    }

    @Override
    public boolean removeFromCertifier(String personId, String certifierId, String reason) {
        return handleCertifier(false, personId, certifierId, reason);
    }

    private boolean handleCertifier(boolean add, String personId, String certifierId, String reason) {

        LOG.debug("Calling handleCertifier for certifierId '{}'", certifierId);

        HandleCertifierType parameters = new HandleCertifierType();
        parameters.setPersonalIdentityNumber(personId);
        parameters.setAddToCertifiers(add);
        parameters.setCertifierId(certifierId);
        parameters.setReason(reason);

        HandleCertifierResponseType response = client.callHandleCertifier(parameters);

        if (!"OK".equals(response.getResult())) {
            LOG.error("handleCertifier returned result '{}' for certifierId '{}'", response.getResult(), certifierId);
            return false;
        }

        return true;
    }

}
