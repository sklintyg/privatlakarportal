package se.inera.privatlakarportal.service.model;

import se.inera.privatlakarportal.auth.PrivatlakarUser;
import se.inera.privatlakarportal.service.model.RegistrationStatus;

/**
 * Created by pebe on 2015-08-25.
 */
public class UserStatus {

    private PrivatlakarUser privatlakarUser;
    private RegistrationStatus status;

    public UserStatus(PrivatlakarUser privatlakarUser, RegistrationStatus status) {
        this.privatlakarUser = privatlakarUser;
        this.status = status;
    }

    public PrivatlakarUser getPrivatlakarUser() {
        return privatlakarUser;
    }

    public RegistrationStatus getStatus() {
        return status;
    }
}
