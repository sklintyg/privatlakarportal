package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationRequest;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationResponseStatus;
import se.inera.privatlakarportal.web.controller.api.dto.GetRegistrationResponse;
import se.inera.privatlakarportal.web.controller.api.dto.Registration;

/**
 * Created by pebe on 2015-06-25.
 */
public interface RegisterService {
    CreateRegistrationResponseStatus createRegistration(Registration registration);
    Registration getRegistration();
}
