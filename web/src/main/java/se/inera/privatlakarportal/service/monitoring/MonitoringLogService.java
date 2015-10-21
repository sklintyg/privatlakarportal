package se.inera.privatlakarportal.service.monitoring;

import se.inera.privatlakarportal.common.model.RegistrationStatus;

/**
 * Interface used when logging to monitoring file. Used to ensure that the log entries are uniform and easy to parse.
 */
public interface MonitoringLogService {

    void logUserRegistered(String id, Long consentVersion, String hsaId, RegistrationStatus registrationStatus);

    void logUserDeleted(String id);

    void logUserLogin(String id, String authenticationScheme);

    void logUserLogout(String id, String authenticationScheme);

    void logUserDetailsChanged(String id);
}
