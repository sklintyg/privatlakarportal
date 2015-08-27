package se.inera.privatlakarportal.service.model;

import se.inera.privatlakarportal.auth.PrivatlakarUser;
import se.inera.privatlakarportal.service.model.RegistrationStatus;

/**
 * Created by pebe on 2015-08-25.
 */
public class User {

    private String personalIdentityNumber;
    private String name;
    private boolean nameFromPuService;
    private String authenticationScheme;
    private RegistrationStatus status;

    public User(PrivatlakarUser privatlakarUser, RegistrationStatus status) {
        personalIdentityNumber = privatlakarUser.getPersonalIdentityNumber();
        name = privatlakarUser.getName();
        nameFromPuService = privatlakarUser.isNameFromPuService();
        authenticationScheme = privatlakarUser.getAuthenticationScheme();
        this.status = status;
    }

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isNameFromPuService() {
        return nameFromPuService;
    }

    public String getAuthenticationScheme() {
        return authenticationScheme;
    }

    public RegistrationStatus getStatus() {
        return status;
    }
}
