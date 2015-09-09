package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.service.model.Registration;

public interface MailService {

    /**
     * sendRegistrationStatusEmail is used for notifying users about pending status of their registration via the
     * specified email address.
     * 
     * @param status
     *            {@link RegistrationStatus} Used to determine what email to send.
     * @param registration
     *            @{link Registration} Registration details such as email address.
     * @throws PrivatlakarportalServiceException
     *             @{link PrivatlakarportalServiceException}
     */
    public void sendRegistrationStatusEmail(RegistrationStatus status, Registration registration) throws PrivatlakarportalServiceException;
}
