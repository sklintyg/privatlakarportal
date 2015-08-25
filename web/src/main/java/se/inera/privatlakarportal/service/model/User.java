package se.inera.privatlakarportal.service.model;

import se.inera.privatlakarportal.auth.PrivatlakarUser;
import se.inera.privatlakarportal.service.model.RegistrationStatus;

/**
 * Created by pebe on 2015-08-25.
 */
public class User {

    private String namn;
    private String authenticationScheme;
    private RegistrationStatus status;

    public User(PrivatlakarUser privatlakarUser, RegistrationStatus status) {
        namn = privatlakarUser.getName();
        authenticationScheme = privatlakarUser.getAuthenticationScheme();
        this.status = status;
    }

    public String getNamn() {
        return namn;
    }

    public String getAuthenticationScheme() {
        return authenticationScheme;
    }

    public RegistrationStatus getStatus() {
        return status;
    }
}
