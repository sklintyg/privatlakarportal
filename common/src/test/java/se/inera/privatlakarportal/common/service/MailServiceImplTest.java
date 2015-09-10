package se.inera.privatlakarportal.common.service;

import static org.junit.Assert.assertEquals;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.common.service.stub.MailStore;
import se.inera.privatlakarportal.common.service.stub.OutgoingMail;
import se.inera.privatlakarportal.persistence.model.Privatlakare;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "dev")
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = MailServiceTestConfig.class)
public class MailServiceImplTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailStore mailStore;

    @Autowired
    private MailService mailService;

    private Privatlakare createTestRegistration() {
        Privatlakare privatlakare = new Privatlakare();
        privatlakare.setPostadress("Testadress");
        privatlakare.setAgarform("Testägarform");
        privatlakare.setEpost("test@test.com");
        return privatlakare;
    }

    @Test
    public void testSendMail() throws MessagingException {
        Privatlakare privatlakare = createTestRegistration();
        mailService.sendRegistrationStatusEmail(RegistrationStatus.AUTHORIZED, privatlakare);
        mailStore.waitForMails(1);

        OutgoingMail oneMail = mailStore.getMails().get(0);
        assertEquals(1, mailStore.getMails().size());
        assertEquals("test@test.com", oneMail.getRecipients().get(0));
        assertEquals("Webcert är klar att användas", oneMail.getSubject());
    }

    @After
    public void cleanMailStore() {
        mailStore.getMails().clear();
    }

}
