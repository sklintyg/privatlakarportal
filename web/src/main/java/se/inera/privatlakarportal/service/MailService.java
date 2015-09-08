package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.service.model.Registration;

public interface MailService {

    public void sendRegistrationStatusEmail(Registration registration);
}
