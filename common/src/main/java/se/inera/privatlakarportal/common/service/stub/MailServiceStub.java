package se.inera.privatlakarportal.common.service.stub;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import se.inera.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.common.service.MailService;
import se.inera.privatlakarportal.persistence.model.Privatlakare;

@Service
public class MailServiceStub implements MailService {

    private static final String AUTHORIZED_BODY =
            "Hej, Dina uppgifter har fortfarande inte kunnat hämtas från Socialstyrelsen." +
            "Du bör kontakta Socialstyrelsen för att verifiera att dina legitimationsuppgifter är korrekta";

    private static final String NOT_AUTHORIZED_BODY =
            "Hej, Dina uppgifter har hämtats från Socialstyrelsen, men behörighet att använda Webcert saknas tyvärr." + 
            "Det kan bero på att du enligt Socialstyrelsen inte är legitimerad läkare, kontakta i så fall Socialstyrelsen." +
            "Det kan också bero på att Inera AB har beslutat att stänga av tjänsten, kontakta i så fall Inera AB för mer information.";

    private static final String WAITING_FOR_HOSP_BODY =
            "Hej, Dina uppgifter har tyvärr fortfarande inte kunnat hämtats från Socialstyrelsen." +
            "Du bör kontakta Socialstyrelsen för att verifiera att dina legitimationsuppgifter är korrekta.";

    private static final Logger LOG = LoggerFactory.getLogger(MailServiceStub.class);

    @Autowired
    private MailStubStore mailStore;

    @Override
    @Async
    public void sendRegistrationStatusEmail(RegistrationStatus status, Privatlakare privatlakare) throws PrivatlakarportalServiceException {
        LOG.info("Sending registration status email to {}", privatlakare.getEpost());
        try {
            mailStore.addMail(privatlakare.getPersonId(), createMessage(status, privatlakare));
        } catch (MessagingException | PrivatlakarportalServiceException e) {
            throw new PrivatlakarportalServiceException(PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM, e.getMessage());
        }
    }

    private String createMessage(RegistrationStatus status, Privatlakare privatlakare) throws MessagingException, PrivatlakarportalServiceException {
        return createHtmlBody(status);
    }

    private String createHtmlBody(RegistrationStatus status) throws MessagingException, PrivatlakarportalServiceException {
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
        return htmlString;
    }
}
