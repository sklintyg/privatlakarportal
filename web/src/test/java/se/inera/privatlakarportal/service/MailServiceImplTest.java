package se.inera.privatlakarportal.service;

import static org.junit.Assert.assertEquals;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.service.mail.stub.MailStore;
import se.inera.privatlakarportal.service.mail.stub.OutgoingMail;
import se.inera.privatlakarportal.service.model.Registration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = MailServiceImplTestConfig.class)
public class MailServiceImplTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailStore mailStore;

    @Autowired
    private MailService mailService;

    private Registration createTestRegistration() {
        Registration registration = new Registration();
        registration.setAdress("Testadress");
        registration.setAgarForm("Testägarform");
        registration.setEpost("test@test.com");
        return registration;
    }

    @Test
    public void testSendMail() throws MessagingException {
        Registration registration = createTestRegistration();
        mailService.sendRegistrationStatusEmail(RegistrationStatus.AUTHORIZED, registration);
        mailStore.waitForMails(1);

        OutgoingMail oneMail = mailStore.getMails().get(0);
        assertEquals(1, mailStore.getMails().size());
        assertEquals("test@test.com", oneMail.getRecipients().get(0));
        assertEquals("Registrering för Webcert", oneMail.getSubject());
    }

    @After
    public void cleanMailStore() {
        mailStore.getMails().clear();
    }

}
