package se.inera.privatlakarportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import se.inera.privatlakarportal.service.model.Registration;

public class MailServiceImpl implements MailService {

    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    @Override
    public void sendRegistrationStatusEmail(Registration registration) {
        LOG.debug("Sending registration status email to {}", registration.getEpost());
    }

}
