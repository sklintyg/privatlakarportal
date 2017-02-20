/**
 * Copyright (C) 2016 Inera AB (http://www.inera.se)
 *
 * This file is part of privatlakarportal (https://github.com/sklintyg/privatlakarportal).
 *
 * privatlakarportal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * privatlakarportal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.privatlakarportal.hsa.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.common.service.MailService;
import se.inera.privatlakarportal.common.utils.PrivatlakareUtils;
import se.inera.privatlakarportal.hsa.monitoring.MonitoringLogService;
import se.inera.privatlakarportal.hsa.services.exception.HospUpdateFailedToContactHsaException;
import se.inera.privatlakarportal.persistence.model.HospUppdatering;
import se.inera.privatlakarportal.persistence.model.LegitimeradYrkesgrupp;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.Specialitet;
import se.inera.privatlakarportal.persistence.repository.HospUppdateringRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;

import javax.xml.ws.WebServiceException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by pebe on 2015-09-03.
 */
@Service
public class HospUpdateServiceImpl implements HospUpdateService {

    private static final int NOTIFY_USER_AFTER_DAYS = 10;

    private static final int REMOVE_REGISTRATION_AFTER_DAYS = 30;

    private static final Logger LOG = LoggerFactory.getLogger(HospUpdateServiceImpl.class);

    @Autowired
    PrivatlakareRepository privatlakareRepository;

    @Autowired
    HospUppdateringRepository hospUppdateringRepository;

    @Autowired
    HospPersonService hospPersonService;

    @Autowired
    MailService mailService;

    @Autowired
    @Qualifier("hsaMonitoringLogService")
    private MonitoringLogService monitoringService;

    @Override
    @Scheduled(cron = "${privatlakarportal.hospupdate.cron}")
    @Transactional
    public void scheduledUpdateHospInformation() {
        String skipUpdate = System.getProperty("scheduled.update.skip", "false");
        LOG.debug("scheduled.update.skip = " + skipUpdate);
        if (skipUpdate.equalsIgnoreCase("true")) {
            LOG.info("Skipping scheduled updateHospInformation");
        } else {
            LOG.info("Starting scheduled updateHospInformation");
            updateHospInformation();
        }
    }

    @Override
    public void updateHospInformation() {
        // Get our last hosp update time from database
        HospUppdatering hospUppdatering = hospUppdateringRepository.findSingle();

        // Get last hosp update time from HSA
        LocalDateTime hsaHospLastUpdate;
        try {
            hsaHospLastUpdate = hospPersonService.getHospLastUpdate();
        } catch (WebServiceException e) {
            LOG.error("Failed to getHospLastUpdate from HSA with exception {}", e);
            return;
        }

        // If hospUppdatering is null this is our first update ever
        if (hospUppdatering == null
                || hospUppdatering.getSenasteHospUppdatering().isBefore(hsaHospLastUpdate)) {

            LOG.info("Hospinformation has been updated in HSA since our last update");

            // Save hosp update time in database
            if (hospUppdatering == null) {
                hospUppdatering = new HospUppdatering(hsaHospLastUpdate);
            } else {
                hospUppdatering.setSenasteHospUppdatering(hsaHospLastUpdate);
            }
            hospUppdateringRepository.save(hospUppdatering);

            // Find privatlakare without hospinformation
            List<Privatlakare> privatlakareList = privatlakareRepository.findNeverHadLakarBehorighet();
            for (Privatlakare privatlakare : privatlakareList) {
                try {
                    RegistrationStatus status = updateHospInformation(privatlakare, true);
                    // Check if information has been updated
                    if (status.equals(RegistrationStatus.AUTHORIZED)
                            || status.equals(RegistrationStatus.NOT_AUTHORIZED)) {
                        privatlakareRepository.save(privatlakare);
                        mailService.sendRegistrationStatusEmail(status, privatlakare);
                    } else if (status.equals(RegistrationStatus.WAITING_FOR_HOSP)) {
                        if (isTimeToRemoveRegistration(privatlakare.getRegistreringsdatum())) {
                            // Remove registration as this is the third attempt without success
                            LOG.info("Removing {} from registration repo", privatlakare.getPersonId());
                            privatlakareRepository.delete(privatlakare);
                            mailService.sendRegistrationRemovedEmail(privatlakare);
                            monitoringService.logRegistrationRemoved(privatlakare.getPersonId());
                        } else if (isTimeToNotifyAboutAwaitingHospStatus(privatlakare.getRegistreringsdatum(),
                                hsaHospLastUpdate)) {
                            LOG.info("Sending AWAITING_HOSP mail to {}", privatlakare.getPersonId());
                            mailService.sendRegistrationStatusEmail(status, privatlakare);
                        }
                    }
                } catch (HospUpdateFailedToContactHsaException e) {
                    LOG.error("Failed to contact HSA with error '{}'", e.getMessage());
                }
            }
        }
    }

    @Override
    public RegistrationStatus updateHospInformation(Privatlakare privatlakare, boolean shouldRegisterInCertifier)
            throws HospUpdateFailedToContactHsaException {

        if (shouldRegisterInCertifier) {
            try {
                if (!hospPersonService.addToCertifier(privatlakare.getPersonId(), privatlakare.getHsaId())) {
                    LOG.error("Failed to call handleCertifier in HSA, this call will be retried at next hosp update cycle.");
                }
            } catch (WebServiceException e) {
                LOG.error("Failed to call handleCertifier in HSA with error {}, this call will be retried at next hosp update cycle.",
                        e.getMessage());
                throw new HospUpdateFailedToContactHsaException(e);
            }
        }

        GetHospPersonResponseType hospPersonResponse;
        try {
            hospPersonResponse = hospPersonService.getHospPerson(privatlakare.getPersonId());
        } catch (WebServiceException e) {
            LOG.error("Failed to call getHospPerson in HSA, this call will be retried at next hosp update cycle.");
            throw new HospUpdateFailedToContactHsaException(e);
        }

        if (hospPersonResponse == null) {
            privatlakare.setLegitimeradeYrkesgrupper(new HashSet<LegitimeradYrkesgrupp>());
            privatlakare.setSpecialiteter(new ArrayList<Specialitet>());
            privatlakare.setForskrivarKod(null);

            monitoringService.logHospWaiting(privatlakare.getPersonId());
            return RegistrationStatus.WAITING_FOR_HOSP;
        } else {
            privatlakare.setSpecialiteter(getSpecialiteter(privatlakare, hospPersonResponse));
            privatlakare.setLegitimeradeYrkesgrupper(getLegitimeradeYrkesgrupper(privatlakare, hospPersonResponse));
            privatlakare.setForskrivarKod(hospPersonResponse.getPersonalPrescriptionCode());

            if (PrivatlakareUtils.hasLakareLegitimation(privatlakare)) {
                monitoringService.logUserAuthorizedInHosp(privatlakare.getPersonId());
                if (!privatlakare.isGodkandAnvandare()) {
                    return RegistrationStatus.NOT_AUTHORIZED;
                }
                return RegistrationStatus.AUTHORIZED;
            } else {
                monitoringService.logUserNotAuthorizedInHosp(privatlakare.getPersonId());
                return RegistrationStatus.NOT_AUTHORIZED;
            }
        }
    }

    @Override
    @Transactional
    public void checkForUpdatedHospInformation(Privatlakare privatlakare) {
        try {
            LocalDateTime hospLastUpdate = hospPersonService.getHospLastUpdate();
            if (privatlakare.getSenasteHospUppdatering() == null
                    || privatlakare.getSenasteHospUppdatering().isBefore(hospLastUpdate)) {

                LOG.debug("Hosp has been updated since last login for privlakare '{}'. Updating hosp information", privatlakare.getPersonId());

                try {
                    updateHospInformation(privatlakare, false);
                    privatlakare.setSenasteHospUppdatering(hospLastUpdate);
                    privatlakareRepository.save(privatlakare);
                } catch (HospUpdateFailedToContactHsaException e) {
                    LOG.error("Failed to update hosp information for privatlakare '{}' due to {}", privatlakare.getPersonId(), e);
                }
            }
        } catch (WebServiceException e) {
            LOG.error("Failed to getHospLastUpdate from HSA in checkForUpdatedHospInformation for privatlakare '{}' due to {}",
                    privatlakare.getPersonId(), e);
        }
    }

    /* Private helpers */
    private boolean isTimeToNotifyAboutAwaitingHospStatus(LocalDateTime registreringsdatum, LocalDateTime lastHospUpdate) {
        return DAYS.between(registreringsdatum.toLocalDate(), lastHospUpdate.toLocalDate()) >= NOTIFY_USER_AFTER_DAYS;
    }

    private boolean isTimeToRemoveRegistration(LocalDateTime registrationDate) {
        return DAYS.between(registrationDate.toLocalDate(), LocalDateTime.now().toLocalDate())
                >= REMOVE_REGISTRATION_AFTER_DAYS;
    }

    private List<Specialitet> getSpecialiteter(Privatlakare privatlakare, GetHospPersonResponseType hospPersonResponse) {
        List<Specialitet> specialiteter = new ArrayList<>();
        if (hospPersonResponse.getSpecialityCodes().getSpecialityCode().size() != hospPersonResponse.getSpecialityNames().getSpecialityName()
                .size()) {
            LOG.error("getHospPerson getSpecialityCodes count "
                    + hospPersonResponse.getSpecialityCodes().getSpecialityCode().size()
                    + "doesn't match getSpecialityNames count '{}' != '{}'"
                    + hospPersonResponse.getSpecialityNames().getSpecialityName().size());
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM,
                    "Inconsistent data from HSA");
        } else {
            for (int i = 0; i < hospPersonResponse.getSpecialityCodes().getSpecialityCode().size(); i++) {
                specialiteter.add(new Specialitet(privatlakare,
                        hospPersonResponse.getSpecialityNames().getSpecialityName().get(i),
                        hospPersonResponse.getSpecialityCodes().getSpecialityCode().get(i)));
            }
        }
        return specialiteter;
    }

    private Set<LegitimeradYrkesgrupp> getLegitimeradeYrkesgrupper(Privatlakare privatlakare, GetHospPersonResponseType hospPersonResponse) {
        Set<LegitimeradYrkesgrupp> legitimeradYrkesgrupper = new HashSet<>();
        if (hospPersonResponse.getHsaTitles().getHsaTitle().size() != hospPersonResponse.getTitleCodes().getTitleCode().size()) {
            LOG.error("getHospPerson getHsaTitles count "
                    + hospPersonResponse.getHsaTitles().getHsaTitle().size()
                    + "doesn't match getTitleCodes count '{}' != '{}'"
                    + hospPersonResponse.getTitleCodes().getTitleCode().size());
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM,
                    "Inconsistent data from HSA");
        } else {
            for (int i = 0; i < hospPersonResponse.getHsaTitles().getHsaTitle().size(); i++) {
                legitimeradYrkesgrupper.add(new LegitimeradYrkesgrupp(privatlakare,
                        hospPersonResponse.getHsaTitles().getHsaTitle().get(i),
                        hospPersonResponse.getTitleCodes().getTitleCode().get(i)));
            }
        }
        return legitimeradYrkesgrupper;
    }

}
