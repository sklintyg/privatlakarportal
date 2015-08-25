package se.inera.privatlakarportal.service.model;

import se.inera.privatlakarportal.auth.PrivatlakarUser;
import se.inera.privatlakarportal.service.model.RegistrationStatus;

/**
 * Created by pebe on 2015-08-25.
 */
public class User {

    private String name;
    private RegistrationStatus status;

    public User(PrivatlakarUser privatlakarUser, RegistrationStatus status) {
        name = privatlakarUser.getName();
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public RegistrationStatus getStatus() {
        return status;
    }
}
