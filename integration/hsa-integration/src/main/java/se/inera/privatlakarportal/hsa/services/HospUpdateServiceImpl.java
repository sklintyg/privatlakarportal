package se.inera.privatlakarportal.hsa.services;

import com.google.common.base.Throwables;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.common.service.MailService;
import se.inera.privatlakarportal.common.utils.PrivatlakareUtils;
import se.inera.privatlakarportal.hsa.services.exception.HospUpdateFailedToContactHsaException;
import se.inera.privatlakarportal.persistence.model.HospUppdatering;
import se.inera.privatlakarportal.persistence.model.LegitimeradYrkesgrupp;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.Specialitet;
import se.inera.privatlakarportal.persistence.repository.HospUppdateringRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.common.model.RegistrationStatus;

import javax.transaction.Transactional;
import javax.xml.ws.WebServiceException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pebe on 2015-09-03.
 */
@Service
public class HospUpdateServiceImpl implements HospUpdateService {

    private static final Logger LOG = LoggerFactory.getLogger(HospUpdateServiceImpl.class);

    @Autowired
    PrivatlakareRepository privatlakareRepository;

    @Autowired
    HospUppdateringRepository hospUppdateringRepository;

    @Autowired
    HospPersonService hospPersonService;

    @Autowired
    MailService mailService;

    @Override
    @Scheduled(cron = "${privatlakarportal.hospupdate.cron}")
    @Transactional
    public void updateHospInformation() {

        LOG.debug("Starting scheduled updateHospInformation");

        // Get our last hosp update time from database
        HospUppdatering hospUppdatering = hospUppdateringRepository.findSingle();

        // Get last hosp update time from HSA
        LocalDateTime hsaHospLastUpdate;
        try {
            hsaHospLastUpdate = hospPersonService.getHospLastUpdate();
        }
        catch(WebServiceException e) {
            LOG.error("Failed to getHospLastUpdate from HSA");
            return;
        }

        // If hospUppdatering is null this is our first update ever
        if (hospUppdatering == null ||
            hospUppdatering.getSenasteHospUppdatering().isBefore(hsaHospLastUpdate)) {

            LOG.info("Hospinformation has been updated in HSA since our last update");

            // Save hosp update time in database
            if (hospUppdatering == null) {
                hospUppdatering = new HospUppdatering(hsaHospLastUpdate);
            }
            else {
                hospUppdatering.setSenasteHospUppdatering(hsaHospLastUpdate);
            }
            hospUppdateringRepository.save(hospUppdatering);

            // Find privatlakare without hospinformation
            List<Privatlakare> privatlakareList = privatlakareRepository.findWithoutLakarBehorighet();
            for(Privatlakare privatlakare : privatlakareList) {

                LOG.info("Checking privatlakare '{}' for updated hosp information", privatlakare.getPersonId());
                try {
                    RegistrationStatus status = updateHospInformation(privatlakare, true);
                    LOG.info("updateHospInformation returned status '{}'", status);

                    // Check if information has been updated
                    if (status == RegistrationStatus.AUTHORIZED ||
                            status == RegistrationStatus.NOT_AUTHORIZED) {
                        privatlakareRepository.save(privatlakare);
                        mailService.sendRegistrationStatusEmail(status, privatlakare);
                    }
                }
                catch(HospUpdateFailedToContactHsaException e) {
                }
            }
        }
    }

    @Override
    public RegistrationStatus updateHospInformation(Privatlakare privatlakare, boolean shouldRegisterInCertifier)
            throws HospUpdateFailedToContactHsaException {
        GetHospPersonResponseType hospPersonResponse;
        try {
            hospPersonResponse = hospPersonService.getHospPerson(privatlakare.getPersonId());
        }
        catch (WebServiceException e) {
            LOG.error("Failed to call getHospPerson in HSA, this call will be retried at next hosp update cycle.");
            throw new HospUpdateFailedToContactHsaException(e);
        }

        if (hospPersonResponse == null) {
            if (shouldRegisterInCertifier) {
                try {
                    if (!hospPersonService.handleCertifier(privatlakare.getPersonId(), privatlakare.getHsaId())) {
                        LOG.error("Failed to call handleCertifier in HSA, this call will be retried at next hosp update cycle.");
                    }
                } catch (WebServiceException e) {
                    LOG.error("Failed to call handleCertifier in HSA, this call will be retried at next hosp update cycle.");
                }
            }
            privatlakare.setLegitimeradeYrkesgrupper(new HashSet<LegitimeradYrkesgrupp>());
            privatlakare.setSpecialiteter(new ArrayList<Specialitet>());
            privatlakare.setForskrivarKod(null);

            return RegistrationStatus.WAITING_FOR_HOSP;
        }
        else {
            List<Specialitet> specialiteter = new ArrayList<>();
            if (hospPersonResponse.getSpecialityCodes().getSpecialityCode().size() !=
                hospPersonResponse.getSpecialityNames().getSpecialityName().size()) {
                LOG.error("getHospPerson getSpecialityCodes count " +
                        hospPersonResponse.getSpecialityCodes().getSpecialityCode().size() +
                        "doesn't match getSpecialityNames count '{}' != '{}'" +
                        hospPersonResponse.getSpecialityNames().getSpecialityName().size());
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
            privatlakare.setSpecialiteter(specialiteter);

            Set<LegitimeradYrkesgrupp> legitimeradYrkesgrupper = new HashSet<>();
            if (hospPersonResponse.getHsaTitles().getHsaTitle().size() !=
                hospPersonResponse.getTitleCodes().getTitleCode().size()) {
                LOG.error("getHospPerson getHsaTitles count " +
                        hospPersonResponse.getHsaTitles().getHsaTitle().size() +
                        "doesn't match getTitleCodes count '{}' != '{}'" +
                        hospPersonResponse.getTitleCodes().getTitleCode().size());
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
            privatlakare.setLegitimeradeYrkesgrupper(legitimeradYrkesgrupper);

            privatlakare.setForskrivarKod(hospPersonResponse.getPersonalPrescriptionCode());

            if (PrivatlakareUtils.hasLakareLegitimation(privatlakare)) {
                // TODO: skicka notifieringsmail: success
                return RegistrationStatus.AUTHORIZED;
            }
            else {
                // TODO: skicka notifieringsmail: success
                return RegistrationStatus.NOT_AUTHORIZED;
            }
        }
    }

    @Override
    @Transactional
    public void checkForUpdatedHospInformation(Privatlakare privatlakare) {
        try {
            LocalDateTime hospLastUpdate = hospPersonService.getHospLastUpdate();
            if (privatlakare.getSenasteHospUppdatering() == null ||
                    privatlakare.getSenasteHospUppdatering().isBefore(hospLastUpdate)) {

                LOG.debug("Hosp has been updated since last login for privlakare '{}'. Updating hosp information", privatlakare.getPersonId());

                try {
                    updateHospInformation(privatlakare, false);
                    privatlakare.setSenasteHospUppdatering(hospLastUpdate);
                    privatlakareRepository.save(privatlakare);
                }
                catch(HospUpdateFailedToContactHsaException e) {
                }
            }
        }
        catch(WebServiceException e) {
            LOG.error("Failed to getHospLastUpdate from HSA in checkForUpdatedHospInformation for privatlakare '{}'", privatlakare.getPersonId());
        }
    }
}
