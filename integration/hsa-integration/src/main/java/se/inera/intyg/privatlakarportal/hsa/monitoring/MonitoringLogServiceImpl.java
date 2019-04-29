/*
 * Copyright (C) 2017 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.hsa.monitoring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import se.inera.intyg.schemas.contract.util.HashUtility;
import se.inera.intyg.privatlakarportal.common.monitoring.util.LogMarkers;


@Service("hsaMonitoringLogService")
public class MonitoringLogServiceImpl implements MonitoringLogService {

    private static final Object SPACE = " ";
    private static final Logger LOG = LoggerFactory.getLogger(MonitoringLogService.class);

    @Override
    public void logHospWaiting(String id) {
        logEvent(MonitoringEvent.HOSP_WAITING, HashUtility.hash(id));
    }

    @Override
    public void logUserAuthorizedInHosp(String id) {
        logEvent(MonitoringEvent.HOSP_AUTHORIZED, HashUtility.hash(id));
    }

    @Override
    public void logUserNotAuthorizedInHosp(String id) {
        logEvent(MonitoringEvent.HOSP_NOT_AUTHORIZED, HashUtility.hash(id));
    }

    @Override
    public void logRegistrationRemoved(String id) {
        logEvent(MonitoringEvent.REGISTRATION_REMOVED, HashUtility.hash(id));
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
        HOSP_WAITING("User '{}' is waiting for HOSP"),
        HOSP_AUTHORIZED("User '{}' is authorized doctor in HOSP"),
        HOSP_NOT_AUTHORIZED("User '{}' is not authorized doctor in HOSP"),
        REGISTRATION_REMOVED("User '{}' exceeded number of registration attempts, removing user");

        private String message;

        MonitoringEvent(String msg) {
            this.message = msg;
        }

        public String getMessage() {
            return message;
        }
    }
}
