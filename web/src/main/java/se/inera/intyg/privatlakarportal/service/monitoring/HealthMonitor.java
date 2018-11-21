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

import io.prometheus.client.Gauge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;
import se.inera.intyg.privatlakarportal.persistence.model.PrivatlakareId;
import se.inera.intyg.privatlakarportal.persistence.repository.PrivatlakareIdRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Time;
import java.util.Collections;

/**
 * Exposes health metrics as Prometheus values. To simplify any 3rd party scraping applications, all metrics produced
 * by this component uses the following conventions:
 *
 * All metrics are prefixed with "health_"
 * All metrics are suffixed with their type, either "_normal" that indicates a boolean value 0 or 1 OR
 * "_value" that indiciates a numeric metric of some kind.
 *
 * Note that NORMAL values uses 0 to indicate OK state and 1 to indicate a problem.
 *
 * @author eriklupander
 */
@EnableScheduling
@Component
public class HealthMonitor {

    private static final String PREFIX = "health_";
    private static final String NORMAL = "_normal";
    private static final String VALUE = "_value";

    private static final long START_TIME = System.currentTimeMillis();

    private static final Gauge UPTIME = Gauge.build()
            .name(PREFIX + "uptime" + VALUE)
            .help("Current uptime in seconds")
            .register();

    private static final Gauge LOGGED_IN_USERS = Gauge.build()
            .name(PREFIX + "logged_in_users" + VALUE)
            .help("Current number of logged in users")
            .register();

    private static final Gauge USED_HSA_IDS = Gauge.build()
            .name(PREFIX + "used_hsa_ids" + VALUE)
            .help("Current number of used HSA ids")
            .register();

    private static final Gauge DB_ACCESSIBLE = Gauge.build()
            .name(PREFIX + "db_accessible" + NORMAL)
            .help("0 == OK 1 == NOT OK")
            .register();

    private static final Gauge HSA_WS_ACCESSIBLE = Gauge.build()
            .name(PREFIX + "hsa_ws_accessible" + NORMAL)
            .help("0 == OK 1 == NOT OK")
            .register();

    private static final long MILLIS_PER_SECOND = 1000L;

    private static final long DELAY = 30000L;
    private static final String CURR_TIME_SQL = "SELECT CURRENT_TIME()";

    @Value("${app.name}")
    private String appName;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HSAWebServiceCalls hsaService;

    @Autowired
    private PrivatlakareIdRepository privatlakareIdRepository;

    @Autowired
    @Qualifier("rediscache")
    private RedisTemplate<Object, Object> redisTemplate;

    // Runs a lua script to count number of keys matching our session keys.
    private RedisScript<Long> redisScript;

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>(
                "return #redis.call('keys','spring:session:"  + appName + ":index:*')", Long.class);
    }

    @Scheduled(fixedDelay = DELAY)
    public void healthCheck() {
        long secondsSinceStart = (System.currentTimeMillis() - START_TIME) / MILLIS_PER_SECOND;

        UPTIME.set(secondsSinceStart);
        LOGGED_IN_USERS.set(countSessions());
        USED_HSA_IDS.set(countNbrOfUsedHsaId());
        DB_ACCESSIBLE.set(checkTimeFromDb() ? 0 : 1);
        HSA_WS_ACCESSIBLE.set(pingHsaWs() ? 0 : 1);
    }

    private int countSessions() {
        Long numberOfUsers = redisTemplate.execute(redisScript, Collections.emptyList());
        return numberOfUsers.intValue();
    }

    private boolean checkTimeFromDb() {
        Time timestamp;
        try {
            Query query = entityManager.createNativeQuery(CURR_TIME_SQL);
            timestamp = (Time) query.getSingleResult();
        } catch (Exception e) {
            return false;
        }
        return timestamp != null;
    }

    private boolean pingHsaWs() {
        try {
            hsaService.callPing();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int countNbrOfUsedHsaId() {
        int nbrOfHsaId = 0;
        Page<PrivatlakareId> ids = privatlakareIdRepository.findAll(new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id")));

        if (!ids.getContent().isEmpty()) {
            nbrOfHsaId = ids.getContent().get(0).getId();
        }
        return nbrOfHsaId;
    }

}
