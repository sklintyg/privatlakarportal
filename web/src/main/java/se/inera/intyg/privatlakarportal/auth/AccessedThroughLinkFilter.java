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

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

public class AccessedThroughLinkFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(AccessedThroughLinkFilter.class);

    private static final int FORBIDDEN = 403;
    private static final String ROOT_URI = "/";
    private static final String SESSION = "SESSION";
    private static final String REFERRER = "referer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        if (isAllowed(request)) {
            filterChain.doFilter(request, response);
        } else {
            LOG.error("Privatlakarportal can not be access directly. Privatlakarportal must be accessed through Webcert due to"
                + "subscription check in Webcert.");
            response.sendError(FORBIDDEN);
        }
    }

    private boolean isAllowed(HttpServletRequest request) {
        if (isRootUri(request) && !hasSessionCookie(request)) {
            return isUrlAccessedThroughLink(request);
        }
        return true;
    }

    private boolean isRootUri(HttpServletRequest request) {
        return request.getRequestURI().equals(ROOT_URI);
    }

    private boolean hasSessionCookie(HttpServletRequest request) {
        return WebUtils.getCookie(request, SESSION) != null;
    }

    private boolean isUrlAccessedThroughLink(HttpServletRequest request) {
        final var referrer = request.getHeader(REFERRER);
        return referrer != null && !referrer.isEmpty();
    }

}
