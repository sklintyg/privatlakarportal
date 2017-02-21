/**
 * Copyright (C) 2016 Inera AB (http://www.inera.se)
 *
 * This file is part of privatlakarportal (https://github.com/sklintyg/privatlakarportal).
 *
 * privatlakarportal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * privatlakarportal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.service.monitoring;

import se.inera.intyg.privatlakarportal.service.monitoring.dto.HealthStatus;

/**
 * Service for checking the health of the application.
 *
 * @author Erik
 *
 */
public interface HealthCheckService {

    /**
     * Check if the database responds.
     *
     * @return
     */
    HealthStatus checkDB();

    /**
     * Check if the connection to HSA is up.
     *
     * @return
     */
    HealthStatus checkHSA();

    /**
     * Returns the applications uptime.
     *
     * @return
     */
    HealthStatus checkUptime();

    /**
     * Returns the applications uptime in human readable format.
     *
     * @return
     */
    String checkUptimeAsString();

    /**
     * Checks the number of logged in users.
     *
     * @return
     */
    HealthStatus checkNbrOfUsers();
    /**
     * Checks the number of used HSA id.
     *
     * @return
     */
    HealthStatus checkNbrOfUsedHsaId();
}
