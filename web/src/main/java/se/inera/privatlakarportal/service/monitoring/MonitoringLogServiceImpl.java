package se.inera.privatlakarportal.service.monitoring;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import se.inera.certificate.logging.LogMarkers;

@Service
public class MonitoringLogServiceImpl implements MonitoringLogService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringLogService.class);
    private static final Object SPACE = " ";
    private static final String DIGEST = "SHA-256";

    private MessageDigest msgDigest;

    @PostConstruct
    public void initMessageDigest() {
        try {
            msgDigest = MessageDigest.getInstance(DIGEST);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void logUserRegistered(String id, String hsaId) {
        logEvent(MonitoringEvent.USER_REGISTERED, hash(id), hsaId);
    }

    @Override
    public void logUserLogin(String id) {
        logEvent(MonitoringEvent.USER_LOGIN, hash(id));
    }

    @Override
    public void logUserLogout(String id) {
        logEvent(MonitoringEvent.USER_LOGOUT, hash(id));
    }

    private void logEvent(MonitoringEvent logEvent, Object... logMsgArgs) {
        LOG.info(LogMarkers.MONITORING, buildMessage(logEvent), logMsgArgs);
    }

    private String buildMessage(MonitoringEvent logEvent) {
        StringBuilder logMsg = new StringBuilder();
        logMsg.append(logEvent.name()).append(SPACE).append(logEvent.getMessage());
        return logMsg.toString();
    }

    public String hash(String id) {
        try {
            msgDigest.update(id.getBytes("UTF-8"));
            byte[] digest = msgDigest.digest();
            return new String(Hex.encodeHex(digest));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    private enum MonitoringEvent {
        USER_REGISTERED("User '{}' registered"),
        USER_LOGIN("Login user '{}' using scheme '{}'"),
        USER_LOGOUT("Logout user '{}' using scheme '{}'");

        private String message;

        private MonitoringEvent(String msg) {
            this.message = msg;
        }

        public String getMessage() {
            return message;
        }
    }
}
