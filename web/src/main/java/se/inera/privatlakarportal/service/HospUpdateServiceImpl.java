package se.inera.privatlakarportal.service;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.hsa.services.HospPersonService;
import se.inera.privatlakarportal.persistence.model.HospUppdatering;
import se.inera.privatlakarportal.persistence.model.LegitimeradYrkesgrupp;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.Specialitet;
import se.inera.privatlakarportal.persistence.repository.HospUppdateringRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.model.HospInformation;
import se.inera.privatlakarportal.service.model.RegistrationStatus;

import javax.transaction.Transactional;
import javax.xml.ws.WebServiceException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pebe on 2015-09-03.
 */
@Service
public class HospUpdateServiceImpl implements HospUpdateService {

    private static final Logger LOG = LoggerFactory.getLogger(HospUpdateServiceImpl.class);

    private static final String LAKARE = "Läkare";

    @Autowired
    PrivatlakareRepository privatlakareRepository;

    @Autowired
    HospUppdateringRepository hospUppdateringRepository;

    @Autowired
    HospPersonService hospPersonService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public HospInformation getHospInformation() {
        GetHospPersonResponseType response = hospPersonService.getHospPerson(userService.getUser().getPersonalIdentityNumber());

        if (response == null) {
            return null;
        }

        HospInformation hospInformation = new HospInformation();
        hospInformation.setPersonalPrescriptionCode(response.getPersonalPrescriptionCode());
        hospInformation.setSpecialityNames(response.getSpecialityNames().getSpecialityName());
        hospInformation.setHsaTitles(response.getHsaTitles().getHsaTitle());

        return hospInformation;
    }

    @Scheduled(cron = "10 * * * * ?")
    @Transactional
    public void updateHospInformation() {

        LOG.info("Starting scheduled updateHospInformation");

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

                RegistrationStatus status = updateHospInformation(privatlakare);
                LOG.info("updateHospInformation returned status '{}'", status);

                // Check if information has been updated
                if (status == RegistrationStatus.AUTHORIZED ||
                    status == RegistrationStatus.NOT_AUTHORIZED) {
                    privatlakareRepository.save(privatlakare);
                }
            }
        }
    }

    public RegistrationStatus updateHospInformation(Privatlakare privatlakare) {
        GetHospPersonResponseType hospPersonResponse;
        try {
            hospPersonResponse = hospPersonService.getHospPerson(privatlakare.getPersonId());
        }
        catch (WebServiceException e) {
            LOG.error("Failed to call getHospPerson in HSA, this call will be retried at next hosp update cycle.");
            return RegistrationStatus.WAITING_FOR_HOSP;
        }

        if (hospPersonResponse == null) {
            try {
                if (!hospPersonService.handleCertifier(privatlakare.getPersonId(), privatlakare.getHsaId())) {
                    LOG.error("Failed to call handleCertifier in HSA, this call will be retried at next hosp update cycle.");
                }
            }
            catch (WebServiceException e) {
                LOG.error("Failed to call handleCertifier in HSA, this call will be retried at next hosp update cycle.");
            }
            return RegistrationStatus.WAITING_FOR_HOSP;
        }
        else {
            Set<Specialitet> specialiteter = new HashSet<Specialitet>();
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

            Set<LegitimeradYrkesgrupp> legitimeradYrkesgrupper = new HashSet<LegitimeradYrkesgrupp>();
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

            if (hospPersonResponse.getHsaTitles().getHsaTitle().contains(LAKARE)) {
                privatlakare.setGodkandAnvandare(true);
                return RegistrationStatus.AUTHORIZED;
            }
            else {
                return RegistrationStatus.NOT_AUTHORIZED;
            }
        }
    }
}
