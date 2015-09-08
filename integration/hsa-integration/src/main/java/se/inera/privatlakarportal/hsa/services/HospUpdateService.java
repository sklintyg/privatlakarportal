package se.inera.privatlakarportal.hsa.services;

import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.common.model.RegistrationStatus;

/**
 * Created by pebe on 2015-09-03.
 */
public interface HospUpdateService {

    public void updateHospInformation();

    RegistrationStatus updateHospInformation(Privatlakare privatlakare);

    void checkForUpdatedHospInformation(Privatlakare privatlakare);
}
