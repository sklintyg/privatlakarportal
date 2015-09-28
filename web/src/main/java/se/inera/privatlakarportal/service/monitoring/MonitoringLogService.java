package se.inera.privatlakarportal.service.monitoring;

/**
 * Interface used when logging to monitoring file. Used to ensure that the log entries are uniform and easy to parse.
 */
public interface MonitoringLogService {

    void logUserRegistered(String id, String hsaId);

    void logUserLogin(String id);

    void logUserLogout(String id);

    String hash(String id);
}
