package se.inera.privatlakarportal.service.model;

/**
 * Created by pebe on 2015-08-17.
 */
public class RegistrationWithHospInformation {
    private Registration registration;

    private HospInformation hospInformation;

    public RegistrationWithHospInformation(Registration registration, HospInformation hospInformation) {
        this.registration = registration;
        this.hospInformation = hospInformation;
    }

    public HospInformation getHospInformation() {
        return hospInformation;
    }

    public void setHospInformation(HospInformation hospInformation) {
        this.hospInformation = hospInformation;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}
