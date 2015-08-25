package se.inera.privatlakarportal.auth;

/**
 * Created by pebe on 2015-08-11.
 */
public class PrivatlakarUser {

    private String personalIdentityNumber;
    private String name;

    public PrivatlakarUser(String personalIdentityNumber, String name) {
        this.personalIdentityNumber = personalIdentityNumber;
        this.name = name;
    }

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public String getName() {
        return name;
    }
}
