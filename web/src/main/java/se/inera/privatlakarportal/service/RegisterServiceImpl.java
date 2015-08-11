package se.inera.privatlakarportal.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.HandleCertifierResponseType;
import se.inera.privatlakarportal.hsa.services.HospPersonService;
import se.inera.privatlakarportal.persistence.model.*;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareIdRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.dto.HospInformation;
import se.inera.privatlakarportal.service.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.service.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationResponseStatus;
import se.inera.privatlakarportal.web.controller.api.dto.Registration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pebe on 2015-06-26.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PrivatlakareRepository privatlakareRepository;

    @Autowired
    private PrivatlakareIdRepository privatlakareidRepository;

    @Autowired
    private HospPersonService hospPersonService;

    private static final String LAKARE = "Läkare";

    @Override
    @Transactional
    public Registration getRegistration() {
        Privatlakare privatlakare = privatlakareRepository.findByPersonId(userService.getUser().getPersonalIdentityNumber());

        if (privatlakare == null) {
            return null;
        }

        Registration registration = new Registration();

        registration.setAdress(privatlakare.getPostadress());
        registration.setAgarForm(privatlakare.getAgarform());
        registration.setArbetsplatskod(privatlakare.getArbetsplatsKod());
        // Detta fält plus några till längre ner kan ha flera värden enligt informationsmodellen men implementeras som bara ett värde.
        if (privatlakare.getBefattningar().size() > 0) {
            registration.setBefattning(privatlakare.getBefattningar().iterator().next().getKod());
        }
        registration.setEpost(privatlakare.getEpost());
        registration.setKommun(privatlakare.getKommun());
        registration.setLan(privatlakare.getLan());
        registration.setPostnummer(privatlakare.getPostnummer());
        registration.setPostort(privatlakare.getPostort());
        registration.setTelefonnummer(privatlakare.getTelefonnummer());
        if (privatlakare.getVardformer().size() > 0) {
            registration.setVardform(privatlakare.getVardformer().iterator().next().getKod());
        }
        registration.setVerksamhetensNamn(privatlakare.getVardgivareNamn());
        if (privatlakare.getVerksamhetstyper().size() > 0) {
            registration.setVerksamhetstyp(privatlakare.getVerksamhetstyper().iterator().next().getKod());
        }

        return registration;
    }

    @Override
    @Transactional
    public HospInformation getHospInformation() {
        GetHospPersonResponseType response = hospPersonService.getHospPerson(userService.getUser().getPersonalIdentityNumber());

        HospInformation hospInformation = new HospInformation();
        hospInformation.setPersonalPrescriptionCode(response.getPersonalPrescriptionCode());
        hospInformation.setSpecialityNames(response.getSpecialityNames().getSpecialityName());
        hospInformation.setHsaTitles(response.getHsaTitles().getHsaTitle());

        return hospInformation;
    }

    @Override
    @Transactional
    public CreateRegistrationResponseStatus createRegistration(Registration registration) {

        if (registration == null || !registration.checkIsValid()) {
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.BAD_REQUEST,
                    "CreateRegistrationRequest is not valid");
        }

        if (privatlakareRepository.findByPersonId(userService.getUser().getPersonalIdentityNumber()) != null) {
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.ALREADY_EXISTS,
                    "Registration already exists");
        }

        // Generate next hsaId
        // Format: "SE" + ineras orgnr (inkl "sekelsiffror", alltså 165565594230) + "-" + "WEBCERT" + femsiffrigt löpnr.
        // PrivatlakareId uses an autoincrement column to get next value
        PrivatlakareId privatlakareId = privatlakareidRepository.save(new PrivatlakareId());
        String hsaId = "SE165565594230-WEBCERT" + StringUtils.leftPad(Integer.toString(privatlakareId.getId()), 5, '0');

        Privatlakare privatlakare = new Privatlakare();

        // TODO Get personId and name from logged in user object
        privatlakare.setPersonId(userService.getUser().getPersonalIdentityNumber());
        privatlakare.setFullstandigtNamn("Namn");

        privatlakare.setAgarform("Privat");
        privatlakare.setArbetsplatsKod(registration.getArbetsplatskod());
        privatlakare.setEnhetsId(hsaId);
        privatlakare.setEnhetsNamn(registration.getVerksamhetensNamn());
        privatlakare.setEpost(registration.getEpost());
        privatlakare.setHsaId(hsaId);
        privatlakare.setKommun(registration.getKommun());
        privatlakare.setLan(registration.getLan());
        privatlakare.setPostadress(registration.getAdress());
        privatlakare.setPostnummer(registration.getPostnummer());
        privatlakare.setPostort(registration.getPostort());
        privatlakare.setTelefonnummer(registration.getTelefonnummer());
        privatlakare.setVardgivareId(hsaId);
        privatlakare.setVardgivareNamn(registration.getVerksamhetensNamn());

        Set<Befattning> befattningar = new HashSet<Befattning>();
        befattningar.add(new Befattning(privatlakare, registration.getBefattning()));
        privatlakare.setBefattningar(befattningar);

        Set<Vardform> vardformer = new HashSet<Vardform>();
        vardformer.add(new Vardform(privatlakare, registration.getVardform()));
        privatlakare.setVardformer(vardformer);

        Set<Verksamhetstyp> verksamhetstyper = new HashSet<Verksamhetstyp>();
        verksamhetstyper.add(new Verksamhetstyp(privatlakare, registration.getVerksamhetstyp()));
        privatlakare.setVerksamhetstyper(verksamhetstyper);

        CreateRegistrationResponseStatus status;
        GetHospPersonResponseType hospPersonResponse = hospPersonService.getHospPerson(privatlakare.getPersonId());
        if (hospPersonResponse == null) {
            status = CreateRegistrationResponseStatus.AUTHENTICATION_INPROGRESS;
            if (!hospPersonService.handleCertifier(privatlakare.getPersonId(), privatlakare.getHsaId())) {
                throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.EXTERNAL_ERROR,
                    "Failed to call handleCertifier in HSA");
            }
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

            status = CreateRegistrationResponseStatus.NOT_AUTHORIZED;
            if (hospPersonResponse.getHsaTitles().getHsaTitle().contains(LAKARE)) {
                privatlakare.setGodkandAnvandare(true);
                status = CreateRegistrationResponseStatus.AUTHORIZED;
            }
        }

        privatlakareRepository.save(privatlakare);

        return status;
    }
}
