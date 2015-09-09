package se.inera.privatlakarportal.service;

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
import se.inera.privatlakarportal.service.model.Registration;

@Service
public class MailServiceImpl implements MailService {

    private static final String AUTHORIZED_BODY =
            "<span>Hej,</span><br/><br/> " +
                    "<span>" +
                    "Dina uppgifter har fortfarande inte kunnat hämtas från Socialstyrelsen." +
                    "Du bör kontakta Socialstyrelsen för att verifiera att dina legitimationsuppgifter är korrekta" +
                    "</span>";

    private static final String NOT_AUTHORIZED_BODY =
            "<span>Hej,</span><br/><br/>" +
                    "<span>" +
                    "Dina uppgifter har hämtats från Socialstyrelsen, men behörighet att använda Webcert saknas tyvärr." +
                    "<br/><br/>" +
                    "Det kan bero på att du enligt Socialstyrelsen inte är legitimerad läkare, kontakta i så fall Socialstyrelsen." +
                    "<br/><br/>" +
                    "Det kan också bero på att Inera AB har beslutat att stänga av tjänsten, kontakta i så fall Inera AB för mer information." +
                    "</span>";

    private static final String WAITING_FOR_HOSP_BODY =
            "<span>Hej,</span>" +
                    "<br/><br/>" +
                    "<span>Dina uppgifter har tyvärr fortfarande inte kunnat hämtats från Socialstyrelsen." +
                    " Du bör kontakta Socialstyrelsen för att verifiera att dina legitimationsuppgifter är korrekta." +
                    "</span>";

    private static final String SUBJECT = "Registrering för Webcert";

    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    private static final String BOTTOM_BODY_CONTENT = "<br/><br/><span><img src='cid:inera_logo'></span>";

    private static final String ENCODING = "text/html; charset=UTF-8";

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;


    @Override
    @Async
    public void sendRegistrationStatusEmail(RegistrationStatus status, Registration registration) throws PrivatlakarportalServiceException {
        LOG.info("Sending registration status email to {}", registration.getEpost());
        try {
            MimeMessage message = createMessage(status, registration);
            mailSender.send(message);
        } catch (MessagingException | PrivatlakarportalServiceException e) {
            throw new PrivatlakarportalServiceException(PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM, e.getMessage());
        }
    }

    private MimeMessage createMessage(RegistrationStatus status, Registration registration) throws MessagingException, PrivatlakarportalServiceException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setHeader("Content-Type", ENCODING);
        message.setSubject(SUBJECT);
        message.setFrom(new InternetAddress(from));
        message.setContent(createHtmlBody(status), "UTF-8");
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(registration.getEpost()));
        return message;
    }

    private Multipart createHtmlBody(RegistrationStatus status) throws MessagingException, PrivatlakarportalServiceException {
        Multipart content = new MimeMultipart("related");
        BodyPart body = new MimeBodyPart();
        String htmlString = null;

        switch (status) {
        case AUTHORIZED:
            htmlString = AUTHORIZED_BODY;
            break;
        case NOT_AUTHORIZED:
            htmlString = NOT_AUTHORIZED_BODY;
            break;
        case NOT_STARTED:
            // TODO: What happens here?
            break;
        case WAITING_FOR_HOSP:
            htmlString = WAITING_FOR_HOSP_BODY;
            break;
        default:
            throw new PrivatlakarportalServiceException(PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM,
                    "Something unforseen happened while sending registration verification email.");
        }

        htmlString += BOTTOM_BODY_CONTENT;
        body.setContent(htmlString, ENCODING);
        content.addBodyPart(body);

        File logo = new File(getLogo());

        MimeBodyPart iconBodyPart = new MimeBodyPart();
        DataSource iconDataSource = new FileDataSource(logo);
        iconBodyPart.setDataHandler(new DataHandler(iconDataSource));
        iconBodyPart.setDisposition(Part.INLINE);
        iconBodyPart.setContentID("<inera_logo>");
        iconBodyPart.addHeader("Content-Type", "image/png");
        content.addBodyPart(iconBodyPart);

        return content;
    }

    private URI getLogo() {
        URI logoUri = null;
        try {
            logoUri = Thread.currentThread().getContextClassLoader().getResource("inera_logo.png").toURI();
        } catch (URISyntaxException e) {
            LOG.error("Unable to find logo for use in email");
            throw new PrivatlakarportalServiceException(PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM, "File not found on server (inera_logo.png");
        }
        return logoUri;
    }

}
