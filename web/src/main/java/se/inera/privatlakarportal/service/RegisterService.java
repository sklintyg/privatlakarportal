package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.service.dto.HospInformation;
import se.inera.privatlakarportal.service.dto.CreateRegistrationResponseStatus;
import se.inera.privatlakarportal.service.dto.Registration;
import se.inera.privatlakarportal.service.dto.SaveRegistrationResponseStatus;

/**
 * Created by pebe on 2015-06-25.
 */
public interface RegisterService {

    CreateRegistrationResponseStatus createRegistration(Registration registration);

    SaveRegistrationResponseStatus saveRegistration(Registration registration);

    Registration getRegistration();

    HospInformation getHospInformation();
}
