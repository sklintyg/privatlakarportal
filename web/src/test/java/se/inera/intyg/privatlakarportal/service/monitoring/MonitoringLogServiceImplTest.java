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
package se.inera.intyg.privatlakarportal.service.monitoring;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.privatlakarportal.common.model.RegistrationStatus;
import se.inera.intyg.privatlakarportal.logging.HashUtility;

@RunWith(MockitoJUnitRunner.class)
public class MonitoringLogServiceImplTest {

    private static final String USER_ID = "USER_ID";
    private static final String AUTHENTICATION_SCHEME = "AUTHENTICATION_SCHEME";
    private static final String HSA_ID = "HSA_ID";
    private static final Long CONSENT_VERSION = 1L;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Spy
    private HashUtility hashUtility;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @InjectMocks
    private MonitoringLogServiceImpl logService;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(hashUtility, "salt", "salt");
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);
    }

    @After
    public void teardown() {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.detachAppender(mockAppender);
    }

    @Test
    public void shouldLogUserRegistered() {
        logService.logUserRegistered(USER_ID, CONSENT_VERSION, HSA_ID, RegistrationStatus.AUTHORIZED);
        verifyLog(Level.INFO,
            "USER_REGISTERED User '" + hashUtility.hash(USER_ID) + "' registered with consent version '1' and hsaId 'HSA_ID', returned status 'AUTHORIZED'");
    }

    @Test
    public void shouldLogUserDeleted() {
        logService.logUserDeleted(USER_ID, HSA_ID);
        verifyLog(Level.INFO, "USER_DELETED User '" + hashUtility.hash(USER_ID) + "' deleted");
    }

    @Test
    public void shouldLogUserLogin() {
        logService.logUserLogin(USER_ID, AUTHENTICATION_SCHEME);
        verifyLog(Level.INFO,
            "USER_LOGIN Login user '" + hashUtility.hash(USER_ID) + "' using scheme 'AUTHENTICATION_SCHEME'");
    }

    @Test
    public void shouldLogUserLogout() {
        logService.logUserLogout(USER_ID, AUTHENTICATION_SCHEME);
        verifyLog(Level.INFO,
            "USER_LOGOUT Logout user '" + hashUtility.hash(USER_ID) + "' using scheme 'AUTHENTICATION_SCHEME'");
    }

    @Test
    public void shouldLogUserDetailsChanged() {
        logService.logUserDetailsChanged(USER_ID, HSA_ID);
        verifyLog(Level.INFO,
            "USER_DETAILS_CHANGED Details for user '" + hashUtility.hash(USER_ID) + "' changed");
    }

    private void verifyLog(Level logLevel, String logMessage) {
        // Verify and capture logging interaction
        verify(mockAppender).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();

        // Verify log
        assertThat(loggingEvent.getLevel(), equalTo(logLevel));
        assertThat(loggingEvent.getFormattedMessage(), equalTo(logMessage));
    }
}
