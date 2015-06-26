package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationRequest;

/**
 * Created by pebe on 2015-06-25.
 */
public interface RegisterService {
    void createRegistration(CreateRegistrationRequest request);
}
