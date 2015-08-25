package se.inera.privatlakarportal.service;

import se.inera.privatlakarportal.auth.PrivatlakarUser;
import se.inera.privatlakarportal.service.model.UserStatus;

public interface UserService {
    public PrivatlakarUser getUser();
    public UserStatus getUserStatus();
}
