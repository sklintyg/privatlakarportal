package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.service.model.*;

/**
 * Created by pebe on 2015-06-25.
 */
public interface RegisterService {

    RegistrationStatus createRegistration(Registration registration);

    SaveRegistrationResponseStatus saveRegistration(Registration registration);

    RegistrationWithHospInformation getRegistration();

    boolean removePrivatlakare(String personId);
}
