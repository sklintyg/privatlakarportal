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
package se.inera.privatlakarportal.service.monitoring;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.jws.WebParam;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import se.inera.privatlakarportal.service.monitoring.dto.HealthStatus;
import se.riv.itintegration.monitoring.rivtabp21.v1.PingForConfigurationResponderInterface;
import se.riv.itintegration.monitoring.v1.ConfigurationType;
import se.riv.itintegration.monitoring.v1.PingForConfigurationResponseType;
import se.riv.itintegration.monitoring.v1.PingForConfigurationType;

/**
 * Implements PingForConfiguration and returns various statuses about the health of the application.
 *
 */
@Service
public class PingForConfigurationResponderImpl implements PingForConfigurationResponderInterface {
    private static final Logger LOG = LoggerFactory.getLogger(PingForConfigurationResponderImpl.class);

    @Value("${buildVersion}")
    private String projectVersion;

    @Value("${buildNumber}")
    private String buildNumberString;

    @Value("${buildTime}")
    private String buildTimeString;

    @Autowired
    private HealthCheckService healthCheck;

    @Override
    public PingForConfigurationResponseType pingForConfiguration(
            @WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true) String logicalAddress,
            @WebParam(partName = "parameters", name = "PingForConfiguration", targetNamespace = "urn:riv:itintegration:monitoring:PingForConfigurationResponder:1") PingForConfigurationType parameters) {
        PingForConfigurationResponseType response = new PingForConfigurationResponseType();
        response.setPingDateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        LOG.info("Version String: " + projectVersion);
        response.setVersion(projectVersion);

        HealthStatus db = healthCheck.checkDB();
        HealthStatus hsa = healthCheck.checkHSA();
        HealthStatus uptime = healthCheck.checkUptime();
        HealthStatus nbrOfUsers = healthCheck.checkNbrOfUsers();

        addConfiguration(response, "buildNumber", buildNumberString);
        addConfiguration(response, "buildTime", buildTimeString);
        addConfiguration(response, "systemUptime", DurationFormatUtils.formatDurationWords(uptime.getMeasurement(), true, true));
        addConfiguration(response, "dbStatus", db.isOk() ? "ok" : "error");
        addConfiguration(response, "hsaStatus", hsa.isOk() ? "ok" : "error");
        addConfiguration(response, "nbrOfUsers", "" + nbrOfUsers.getMeasurement());

        return response;
    }

    private void addConfiguration(PingForConfigurationResponseType response, String name, String value) {
        ConfigurationType conf = new ConfigurationType();
        conf.setName(name);
        conf.setValue(value);
        response.getConfiguration().add(conf);
    }

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        LOG.info("PingForConfiguration loaded");
    }
}
