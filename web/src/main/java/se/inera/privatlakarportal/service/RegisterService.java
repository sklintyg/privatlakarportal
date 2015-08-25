package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.service.model.HospInformation;
import se.inera.privatlakarportal.service.model.CreateRegistrationResponseStatus;
import se.inera.privatlakarportal.service.model.Registration;
import se.inera.privatlakarportal.service.model.SaveRegistrationResponseStatus;

/**
 * Created by pebe on 2015-06-25.
 */
public interface RegisterService {

    CreateRegistrationResponseStatus createRegistration(Registration registration);

    SaveRegistrationResponseStatus saveRegistration(Registration registration);

    se.inera.privatlakarportal.service.model.RegistrationWithHospInformation getRegistration();

    HospInformation getHospInformation();

    boolean removePrivatlakare(String personId);
}
