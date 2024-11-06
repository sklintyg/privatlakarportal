package se.inera.intyg.privatlakarportal.auth;

public enum LoginMethod {
    ELEG, FAKE;

    public String value() {
        return name();
    }

}