package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.service.model.Registration;

public interface MailService {

    public void sendRegistrationStatusEmail(RegistrationStatus status, Registration registration) throws PrivatlakarportalServiceException;
}
