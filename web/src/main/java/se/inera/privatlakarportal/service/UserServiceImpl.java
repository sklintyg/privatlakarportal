package se.inera.privatlakarportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.inera.privatlakarportal.auth.PrivatlakarUser;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.model.RegistrationStatus;
import se.inera.privatlakarportal.service.model.User;

import javax.transaction.Transactional;

/**
 * Created by pebe on 2015-08-11.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PrivatlakareRepository privatlakareRepository;

    @Override
    public PrivatlakarUser getUser() {
        return (PrivatlakarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    @Transactional
    public User getUserWithStatus() {
        PrivatlakarUser privatlakarUser = getUser();

        RegistrationStatus status = RegistrationStatus.UNKNOWN;
        if (privatlakarUser != null) {
            Privatlakare privatlakare = privatlakareRepository.findByPersonId(privatlakarUser.getPersonalIdentityNumber());

            if (privatlakare == null) {
                status = RegistrationStatus.NOT_STARTED;
            }
            else if (!privatlakare.isGodkandAnvandare()) {
                status = RegistrationStatus.WAITING_FOR_HOSP;
            }
            else {
                status = RegistrationStatus.COMPLETE;
            }
        }

        return new User(privatlakarUser, status);
    }

}
