/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.persistence.config;

import static se.inera.intyg.privatlakarportal.persistence.config.PersistenceConfigBase.BASE_PACKAGES;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("h2")
@ComponentScan(BASE_PACKAGES)
@EnableJpaRepositories(basePackages = BASE_PACKAGES)
public class PersistenceConfigDev extends PersistenceConfigBase {

    @Value("${db.httpPort}")
    private String databaseHttpPort;

    private static final Logger LOG = LoggerFactory.getLogger(PersistenceConfigDev.class);

    @Bean(destroyMethod = "stop")
    Server h2WebServer() throws SQLException {
        LOG.info("Starting H2 Web Server Console on http://127.0.0.1:{}", databaseHttpPort);
        final Server server = Server.createWebServer("-web", "-webAllowOthers", "-webPort", databaseHttpPort);
        server.start();
        return server;
    }

}
