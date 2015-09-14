package se.inera.privatlakarportal.common.service;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import se.inera.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.persistence.model.Privatlakare;

@Service
public class MailServiceImpl implements MailService {

    @Value("${mail.from}")
    private String from;

    @Value("${mail.content.approved.body}")
    private String approvedBody;

    @Value("${mail.content.rejected.body}")
    private String notApprovedBody;

    @Value("${mail.content.pending.body}")
    private String awaitingHospBody;

    @Value("${mail.content.approved.subject}")
    private String approvedSubject;

    @Value("${mail.content.rejected.subject}")
    private String notApprovedSubject;

    @Value("${mail.content.pending.subject}")
    private String awaitingHospSubject;

    private static final String INERA_LOGO = "inera_logo.png";

    private static final String BOTTOM_BODY_CONTENT = "<br/><br/><span><img src='cid:inera_logo'></span>";

    private static final String ENCODING = "text/html; charset=UTF-8";

    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Async
    public void sendRegistrationStatusEmail(RegistrationStatus status, Privatlakare privatlakare) throws PrivatlakarportalServiceException {
        LOG.info("Sending registration status email to {}", privatlakare.getEpost());
        try {
            MimeMessage message = createMessage(status, privatlakare);
            mailSender.send(message);
        } catch (MessagingException | PrivatlakarportalServiceException e) {
            throw new PrivatlakarportalServiceException(PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM, e.getMessage());
        }
    }

    private MimeMessage createMessage(RegistrationStatus status, Privatlakare privatlakare) throws MessagingException, PrivatlakarportalServiceException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setHeader("Content-Type", ENCODING);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(privatlakare.getEpost()));

        buildEmailContent(message, status);
        return message;
    }

    private void buildEmailContent(MimeMessage message, RegistrationStatus status) throws MessagingException, PrivatlakarportalServiceException {
        Multipart content = new MimeMultipart("related");
        BodyPart body = new MimeBodyPart();
        String subjectString = null; 
        String htmlBodyString = null;

        // Set correct content and subject depending on status
        switch (status) {
        case AUTHORIZED:
            subjectString = approvedSubject;
            htmlBodyString = approvedBody;
            break;
        case NOT_AUTHORIZED:
            subjectString = notApprovedSubject;
            htmlBodyString = notApprovedBody;
            break;
        case NOT_STARTED:
            // TODO: What happens here?
            break;
        case WAITING_FOR_HOSP:
            subjectString = awaitingHospSubject;
            htmlBodyString = awaitingHospBody;
            break;
        default:
            throw new PrivatlakarportalServiceException(PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM,
                    "Something unforseen happened while sending registration verification email.");
        }

        htmlBodyString += BOTTOM_BODY_CONTENT;
        body.setContent(htmlBodyString, ENCODING);
        content.addBodyPart(body);

        File logo = new File(getLogo());

        // Add attachment and markup for logo 
        MimeBodyPart iconBodyPart = new MimeBodyPart();
        DataSource iconDataSource = new FileDataSource(logo);
        iconBodyPart.setDataHandler(new DataHandler(iconDataSource));
        iconBodyPart.setDisposition(Part.INLINE);
        iconBodyPart.setContentID("<inera_logo>");
        iconBodyPart.addHeader("Content-Type", "image/png");
        content.addBodyPart(iconBodyPart);

        message.setContent(content, "UTF-8");
        message.setSubject(subjectString, "UTF-8");
    }

    private URI getLogo() {
        URI logoUri = null;
        try {
            logoUri = Thread.currentThread().getContextClassLoader().getResource(INERA_LOGO).toURI();
            LOG.debug("Got uri {} when attempting to read INERA_LOGO", logoUri);
        } catch (URISyntaxException e) {
            LOG.error("Unable to find logo for use in email");
            throw new PrivatlakarportalServiceException(PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM, "File not found on server" + INERA_LOGO);
        }
        return logoUri;
    }

}
