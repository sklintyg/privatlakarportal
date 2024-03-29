/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.service.monitoring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.inera.intyg.privatlakarportal.common.model.RegistrationStatus;
import se.inera.intyg.infra.monitoring.logging.LogMarkers;
import se.inera.intyg.schemas.contract.util.HashUtility;

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
    public void logUserErased(String hsaId) {
        logEvent(MonitoringEvent.USER_DELETED, hsaId);
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

        private final String message;

        MonitoringEvent(String msg) {
            this.message = msg;
        }

        public String getMessage() {
            return message;
        }
    }
}
