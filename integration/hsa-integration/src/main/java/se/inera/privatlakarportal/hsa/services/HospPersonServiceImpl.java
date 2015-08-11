package se.inera.privatlakarportal.hsa.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.inera.ifv.hsawsresponder.v3.*;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;

@Service
public class HospPersonServiceImpl implements HospPersonService {

    private static final Logger LOG = LoggerFactory.getLogger(HospPersonServiceImpl.class);

    @Autowired
    private HSAWebServiceCalls client;

    /*
     * (non-Javadoc)
     *
     * @see se.inera.webcert.hsa.services.HsaPersonService#getHsaPersonInfo(java.lang.String)
     */
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
}
