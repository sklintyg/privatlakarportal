package se.inera.privatlakarportal.web.controller.api.dto;

import se.inera.privatlakarportal.service.model.RegistrationStatus;
import se.inera.privatlakarportal.service.model.UserStatus;

/**
 * Created by pebe on 2015-08-21.
 */
public class GetUserResponse {

    private String name;
    private RegistrationStatus registrationStatus;

    public GetUserResponse(UserStatus userStatus) {
        this.name = userStatus.getPrivatlakarUser().getName();
        this.registrationStatus = userStatus.getStatus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}
