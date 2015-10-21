package se.inera.privatlakarportal.service.monitoring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import se.inera.certificate.logging.HashUtility;
import se.inera.certificate.logging.LogMarkers;
import se.inera.privatlakarportal.common.model.RegistrationStatus;

@Service("webMonitoringLogService")
public class MonitoringLogServiceImpl implements MonitoringLogService {

    private static final Object SPACE = " ";
    private static final Logger LOG = LoggerFactory.getLogger(MonitoringLogService.class);

    @Override
    public void logUserRegistered(String id, Long consentVersion, String hsaId, RegistrationStatus registrationStatus) {
        logEvent(MonitoringEvent.USER_REGISTERED, HashUtility.hash(id), consentVersion, hsaId, registrationStatus);
    }

    @Override
    public void logUserDeleted(String id) {
        logEvent(MonitoringEvent.USER_DELETED, HashUtility.hash(id));
    }

    @Override
    public void logUserLogin(String id, String authenticationScheme) {
        logEvent(MonitoringEvent.USER_LOGIN, HashUtility.hash(id), authenticationScheme);
    }

    @Override
    public void logUserLogout(String id, String authenticationScheme) {
        logEvent(MonitoringEvent.USER_LOGOUT, HashUtility.hash(id), authenticationScheme);
    }

    @Override
    public void logUserDetailsChanged(String id) {
        logEvent(MonitoringEvent.USER_DETAILS_CHANGED, HashUtility.hash(id));
    }

    private void logEvent(MonitoringEvent logEvent, Object... logMsgArgs) {
        LOG.info(LogMarkers.MONITORING, buildMessage(logEvent), logMsgArgs);
    }

    private String buildMessage(MonitoringEvent logEvent) {
        StringBuilder logMsg = new StringBuilder();
        logMsg.append(logEvent.name()).append(SPACE).append(logEvent.getMessage());
        return logMsg.toString();
    }

    private enum MonitoringEvent {
        USER_REGISTERED("User '{}' registered with consent version '{}' and hsaId '{}', returned status '{}'"),
        USER_DELETED("User '{}' deleted"),
        USER_LOGIN("Login user '{}' using scheme '{}'"),
        USER_LOGOUT("Logout user '{}' using scheme '{}'"),
        USER_DETAILS_CHANGED("Details for user '{}' changed");

        private String message;

        private MonitoringEvent(String msg) {
            this.message = msg;
        }

        public String getMessage() {
            return message;
        }
    }
}
