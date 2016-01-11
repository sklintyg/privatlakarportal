package se.inera.privatlakarportal.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Created by eriklupander on 2015-06-16.
 */
public class FakeElegAuthenticationToken extends AbstractAuthenticationToken {

    private FakeElegCredentials fakeElegCredentials;

    public FakeElegAuthenticationToken(FakeElegCredentials fakeElegCredentials) {
        super(null);
        this.fakeElegCredentials = fakeElegCredentials;
    }

    @Override
    public Object getCredentials() {
        return fakeElegCredentials;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
