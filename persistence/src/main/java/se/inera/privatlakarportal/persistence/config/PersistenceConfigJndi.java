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
package se.inera.privatlakarportal.persistence.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiTemplate;

import se.inera.privatlakarportal.persistence.liquibase.DbChecker;

@Configuration
@Profile("!dev")
@ComponentScan("se.inera.privatlakarportal.persistence")
@EnableJpaRepositories(basePackages = "se.inera.privatlakarportal.persistence")
public class PersistenceConfigJndi extends PersistenceConfig {

    @Bean(destroyMethod = "close")
    DataSource jndiDataSource() {
        DataSource dataSource = null;
        JndiTemplate jndi = new JndiTemplate();
        try {
            dataSource = (DataSource) jndi.lookup("java:comp/env/jdbc/privatlakarportal");
        } catch (NamingException e) {

        }
        return dataSource;
    }

    @Bean(name = "dbUpdate")
    DbChecker checkDb(DataSource dataSource) {
        return new DbChecker(dataSource, "changelog/changelog.xml");
    }
}
