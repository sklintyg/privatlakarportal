/*
 * Copyright (C) 2019 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.infra.integration.pu.model.PersonSvar;
import se.inera.intyg.infra.integration.pu.services.PUService;
import se.inera.intyg.privatlakarportal.auth.PrivatlakarUser;
import se.inera.intyg.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.intyg.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.intyg.privatlakarportal.common.model.RegistrationStatus;
import se.inera.intyg.schemas.contract.util.HashUtility;
import se.inera.intyg.privatlakarportal.common.utils.PrivatlakareUtils;
import se.inera.intyg.privatlakarportal.persistence.model.Privatlakare;
import se.inera.intyg.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.intyg.privatlakarportal.service.model.User;
import se.inera.intyg.schemas.contract.Personnummer;

import java.util.Optional;

/**
 * Created by pebe on 2015-08-11.
 */
@Service
@Transactional(transactionManager = "transactionManager")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PrivatlakareRepository privatlakareRepository;

    @Autowired
    private PUService puService;

    @Override
    public PrivatlakarUser getUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        return (PrivatlakarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public User getUserWithStatus() {
        PrivatlakarUser privatlakarUser = getUser();

        if (privatlakarUser == null) {
            throw new PrivatlakarportalServiceException(PrivatlakarportalErrorCodeEnum.NOT_FOUND, "No logged in user");
        }

        RegistrationStatus status;
        Privatlakare privatlakare = privatlakareRepository.findByPersonId(privatlakarUser.getPersonalIdentityNumber());
        if (privatlakare == null) {
            status = RegistrationStatus.NOT_STARTED;
        } else if (!privatlakare.isGodkandAnvandare()) {
            status = RegistrationStatus.NOT_AUTHORIZED;
        } else if (!PrivatlakareUtils.hasLakareLegitimation(privatlakare)) {
            status = RegistrationStatus.WAITING_FOR_HOSP;
        } else {
            status = RegistrationStatus.AUTHORIZED;
        }

        boolean nameUpdated = false;
        PersonSvar.Status personSvarStatus;
        try {
            Optional<Personnummer> personnummerOptional = Personnummer
                    .createPersonnummer(privatlakarUser.getPersonalIdentityNumber());

            if (personnummerOptional.isPresent()) {
                PersonSvar personSvar = puService.getPerson(personnummerOptional.get());
                personSvarStatus = personSvar.getStatus();

                if (personSvar.getStatus() == PersonSvar.Status.FOUND && personSvar.getPerson() != null) {
                    String name = personSvar.getPerson().getFornamn() + " " + personSvar.getPerson().getEfternamn();
                    privatlakarUser.updateNameFromPuService(name);

                    // Check if name has changed and update in database
                    if (privatlakare != null && !name.equals(privatlakare.getFullstandigtNamn())) {
                        LOG.info("Updated name for user '{}'", HashUtility.hash(privatlakarUser.getPersonalIdentityNumber()));
                        privatlakare.setFullstandigtNamn(name);
                        privatlakareRepository.save(privatlakare);
                        nameUpdated = true;
                    }

                } else if (personSvar.getStatus() == PersonSvar.Status.NOT_FOUND) {
                    LOG.warn("Person '{}' not found in puService", HashUtility.hash(privatlakarUser.getPersonalIdentityNumber()));
                } else {
                    LOG.error("puService returned error status for personId '{}'",
                            HashUtility.hash(privatlakarUser.getPersonalIdentityNumber()));
                }
            } else {
                LOG.error("puService could not parse personnummer, returning error status for personId '{}'",
                        HashUtility.hash(privatlakarUser.getPersonalIdentityNumber()));
                personSvarStatus = PersonSvar.Status.ERROR;
            }
        } catch (RuntimeException e) {
            LOG.error("Failed to contact puService", e);
            personSvarStatus = PersonSvar.Status.ERROR;
        }

        return new User(privatlakarUser, personSvarStatus, status, nameUpdated);
    }

}
