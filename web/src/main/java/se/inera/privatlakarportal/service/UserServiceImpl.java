package se.inera.privatlakarportal.service;

import org.springframework.stereotype.Service;
import se.inera.privatlakarportal.auth.PrivatlakarUser;

/**
 * Created by pebe on 2015-08-11.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public PrivatlakarUser getUser() {
        return new PrivatlakarUser("191212121212");
    }
}