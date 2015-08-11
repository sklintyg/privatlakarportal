/*
 * Inera Medcert - Sjukintygsapplikation
 *
 * Copyright (C) 2010-2011 Inera AB (http://www.inera.se)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package se.inera.ifv.privatlakarportal.spi.authorization.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3.wsaddressing10.AttributedURIType;

import se.inera.ifv.hsaws.v3.HsaWsResponderInterface;
import se.inera.ifv.hsawsresponder.v3.*;

import com.google.common.base.Throwables;

public class HSAWebServiceCalls {

    @Autowired
    private HsaWsResponderInterface hsaWebServiceClient;

    private static final Logger LOG = LoggerFactory.getLogger(HSAWebServiceCalls.class);

    private AttributedURIType logicalAddressHeader = new AttributedURIType();

    private AttributedURIType messageId = new AttributedURIType();

    /**
     * @param hsaLogicalAddress the hsaLogicalAddress to set
     */
    public void setHsaLogicalAddress(String hsaLogicalAddress) {
        logicalAddressHeader.setValue(hsaLogicalAddress);
    }

    /**
     * Help method to test access to HSA.
     *
     * @throws Exception
     */
    public void callPing() throws Exception {

        try {
            PingType pingtype = new PingType();
            PingResponseType response = hsaWebServiceClient.ping(logicalAddressHeader, messageId, pingtype);
            LOG.debug("Response:" + response.getMessage());

        } catch (Throwable ex) {
            LOG.warn("Exception={}", ex.getMessage());
            throw new Exception(ex);
        }
    }

    public GetHospPersonResponseType callGetHospPerson(GetHospPersonType parameters) {
        try {
            GetHospPersonResponseType response = hsaWebServiceClient.getHospPerson(logicalAddressHeader, messageId, parameters);
            return response;
        } catch (Throwable ex) {
            LOG.error("Failed to call callGetHsaPerson with hsaId '{}'", parameters.getPersonalIdentityNumber());
            Throwables.propagate(ex);
            return null;
        }
    }

}
