package se.inera.privatlakarportal.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 * Implementation of SessioRegistry that performs audit logging of login and logout.
 * 
 * @author npet
 *
 */
public class LoggingSessionRegistryImpl extends SessionRegistryImpl {
    
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingSessionRegistryImpl.class);
}
