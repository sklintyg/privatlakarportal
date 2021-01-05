/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opensaml.saml2.core.NameID;
import org.springframework.security.saml.SAMLCredential;
import se.inera.intyg.infra.integration.pu.services.PUService;

/**
 * Created by eriklupander on 2015-06-25.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElegUserDetailsServiceTest extends BaseSAMLCredentialTest {

    private static final String LOCAL_ENTITY_ID = "localEntityId";
    private static final String REMOTE_ENTITY_ID = "remoteEntityId";

    @Mock
    private PUService puService;

    @InjectMocks
    private ElegUserDetailsService elegService;

    @BeforeClass
    public static void readSamlAssertions() throws Exception {
        bootstrapSamlAssertions();
    }

    @Test
    public void testLoadUserBySAML() {

        NameID nameId = mock(NameID.class);
        PrivatlakarUser priv = (PrivatlakarUser) elegService
            .loadUserBySAML(new SAMLCredential(nameId, assertionPrivatlakare, REMOTE_ENTITY_ID, LOCAL_ENTITY_ID));

        assertEquals("197705232382", priv.getPersonalIdentityNumber());
        assertEquals("Frida Kranstege", priv.getName());
    }
}
