/*
 * Copyright (C) 2018 Inera AB (http://www.inera.se)
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

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;
import se.inera.intyg.privatlakarportal.persistence.model.PrivatlakareId;
import se.inera.intyg.privatlakarportal.persistence.repository.PrivatlakareIdRepository;
import se.inera.intyg.privatlakarportal.service.monitoring.dto.HealthStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Time;
import java.util.List;

/**
 * Service for getting the health status of the application.
 *
 */
@Service("healthCheckService")
public class HealthCheckServiceImpl implements HealthCheckService {

    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckServiceImpl.class);

    private static final long START_TIME = System.currentTimeMillis();

    private static final String CURR_TIME_SQL = "SELECT CURRENT_TIME()";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HSAWebServiceCalls hsaService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private PrivatlakareIdRepository privatlakareIdRepository;

    @Override
    public HealthStatus checkHSA() {
        boolean ok;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            hsaService.callPing();
            ok = true;
        } catch (Exception e) {
            ok = false;
        }
        stopWatch.stop();
        HealthStatus status = createStatusWithTiming(ok, stopWatch);
        logStatus("getHsaStatus", status);
        return status;
    }

    @Override
   //  @Transactional
    public HealthStatus checkDB() {
        boolean ok;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ok = checkTimeFromDb();
        stopWatch.stop();
        HealthStatus status = createStatusWithTiming(ok, stopWatch);
        logStatus("getDbStatus", status);
        return status;
    }

    @Override
    public HealthStatus checkNbrOfUsers() {
        boolean ok;
        long size = -1;
        try {
            List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
            size = allPrincipals.size();
            ok = true;
        } catch (Exception e) {
            ok = false;
        }

        String result = ok ? "OK" : "FAIL";
        LOG.info("Operation checkNbrOfUsers completed with result {}, nbr of users is {}", result, size);

        return new HealthStatus(size, ok);
    }

    @Override
    public HealthStatus checkNbrOfUsedHsaId() {
        int nbrOfHsaId = 0;
        Page<PrivatlakareId> ids = privatlakareIdRepository.findAll(new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id")));

        if (!ids.getContent().isEmpty()) {
            nbrOfHsaId = ids.getContent().get(0).getId();
        }
        return new HealthStatus(nbrOfHsaId, true);
    }

    @Override
    public HealthStatus checkUptime() {
        long uptime = System.currentTimeMillis() - START_TIME;
        LOG.info("Current system uptime is {}", DurationFormatUtils.formatDurationWords(uptime, true, true));
        return new HealthStatus(uptime, true);
    }

    @Override
    public String checkUptimeAsString() {
        HealthStatus uptime = checkUptime();
        return DurationFormatUtils.formatDurationWords(uptime.getMeasurement(), true, true);
    }

    private boolean checkTimeFromDb() {
        Time timestamp;
        try {
            Query query = entityManager.createNativeQuery(CURR_TIME_SQL);
            timestamp = (Time) query.getSingleResult();
        } catch (Exception e) {
            LOG.error("checkTimeFromDb failed with exception: " + e.getMessage());
            return false;
        }
        return timestamp != null;

    }

    private void logStatus(String operation, HealthStatus status) {
        String result = status.isOk() ? "OK" : "FAIL";
        LOG.info("Operation {} completed with result {} in {} ms", new Object[] { operation, result, status.getMeasurement() });
    }

    private HealthStatus createStatusWithTiming(boolean ok, StopWatch stopWatch) {
        return new HealthStatus(stopWatch.getTime(), ok);
    }

}
