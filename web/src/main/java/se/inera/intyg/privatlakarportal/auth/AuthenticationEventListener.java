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

package se.inera.intyg.privatlakarportal.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import se.inera.intyg.privatlakarportal.logging.MdcLogConstants;
import se.inera.intyg.privatlakarportal.service.monitoring.MonitoringLogService;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationEventListener {

    private final MonitoringLogService monitoringLogService;

    @EventListener
    public void onLoginSuccess(InteractiveAuthenticationSuccessEvent success) {
        updateMDCWithNewSessionId();

        final var privatlakarUser = getUser(success.getAuthentication().getPrincipal());
        privatlakarUser.ifPresent(user ->
            monitoringLogService.logUserLogin(
                user.getPersonalIdentityNumber(),
                user.getAuthenticationScheme()
            )
        );
    }

    /**
     * Spring Security will by default invalidate the old session and create a new one after authentication.
     * It’s a security feature to protect against session fixation attacks.
     * Update the MDC with the new session id.
     */
    private static void updateMDCWithNewSessionId() {
        final var attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            final var request = attrs.getRequest();
            final var session = request.getSession(false);

            if (session != null && session.getId() != null) {
                final var sessionId = session.getId();
                final var encodedSessionId = Base64.getEncoder().encodeToString(sessionId.getBytes(StandardCharsets.UTF_8));
                MDC.put(MdcLogConstants.SESSION_ID_KEY, encodedSessionId);
            }
        }
    }

    @EventListener
    public void onLogoutSuccess(LogoutSuccessEvent success) {
        final var privatlakarUser = getUser(success.getAuthentication().getPrincipal());
        privatlakarUser.ifPresent(user ->
            monitoringLogService.logUserLogout(
                user.getPersonalIdentityNumber(),
                user.getAuthenticationScheme()
            )
        );
    }

    private static Optional<PrivatlakarUser> getUser(Object principal) {
        if (principal instanceof PrivatlakarUser user) {
            return Optional.of(user);
        }
        log.warn("Invalid principal [{}]", principal.getClass().getSimpleName());
        return Optional.empty();
    }
}
