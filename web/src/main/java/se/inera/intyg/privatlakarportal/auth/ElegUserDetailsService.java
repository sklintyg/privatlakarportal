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
package se.inera.intyg.privatlakarportal.auth;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import se.inera.intyg.infra.integration.pu.services.PUService;

/**
 * Created by eriklupander on 2015-06-16.
 */
@Component
public class ElegUserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(ElegUserDetailsService.class);

    @Autowired
    PUService puService;

    public Object buildUserPrincipal(String personId, String name, String authMethod) {
        try {
            return new PrivatlakarUser(personId, name);
        } catch (AuthenticationException e) {
            LOG.error("Got AuthenticationException, with message {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.error("Error building user {}, failed with message {}", e.getMessage());
            throw e;
        }
    }
}