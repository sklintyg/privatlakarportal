package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.service.model.HospInformation;
import se.inera.privatlakarportal.service.model.RegistrationStatus;

/**
 * Created by pebe on 2015-09-03.
 */
public interface HospUpdateService {

    HospInformation getHospInformation();

    public RegistrationStatus updateHospInformation(Privatlakare privatlakare);

}
