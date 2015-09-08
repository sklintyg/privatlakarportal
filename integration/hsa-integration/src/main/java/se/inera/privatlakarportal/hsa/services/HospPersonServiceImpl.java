package se.inera.privatlakarportal.hsa.services;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.inera.ifv.hsawsresponder.v3.*;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;

@Service
public class HospPersonServiceImpl implements HospPersonService {

    private static final Logger LOG = LoggerFactory.getLogger(HospPersonServiceImpl.class);

    @Autowired
    private HSAWebServiceCalls client;

    @Autowired
    PrivatlakareRepository privatlakareRepository;

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
    public boolean handleCertifier(String personId, String certifierId) {

        LOG.debug("Getting handleCertifier for certifierId '{}'", certifierId);

        HandleCertifierType parameters = new HandleCertifierType();
        parameters.setPersonalIdentityNumber(personId);
        parameters.setAddToCertifiers(true);
        parameters.setCertifierId(certifierId);

        HandleCertifierResponseType response = client.callHandleCertifier(parameters);

        if (!response.getResult().equals("OK")) {
            LOG.error("handleCertifier returned result '{}' for certifierId '{}'", response.getResult(), certifierId);
            return false;
        }

        return true;
    }

    @Override
    public LocalDateTime getHospLastUpdate() {

        LOG.debug("Calling getHospLastUpdate");

        GetHospLastUpdateType parameters = new GetHospLastUpdateType();

        GetHospLastUpdateResponseType response = client.callGetHospLastUpdate(parameters);

        return response.getLastUpdate();
    }

}
