package se.inera.privatlakarportal.auth;

/**
 * Created by pebe on 2015-08-11.
 */
public class PrivatlakarUser {

    private String personalIdentityNumber;

    public PrivatlakarUser(String personalIdentityNumber) {
        this.personalIdentityNumber = personalIdentityNumber;
    }

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }
}
