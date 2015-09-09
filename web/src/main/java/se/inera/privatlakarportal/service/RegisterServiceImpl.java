package se.inera.privatlakarportal.service;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.privatlakarportal.common.model.RegistrationStatus;
import se.inera.privatlakarportal.hsa.services.HospPersonService;
import se.inera.privatlakarportal.hsa.services.HospUpdateService;
import se.inera.privatlakarportal.persistence.model.*;
import se.inera.privatlakarportal.persistence.repository.MedgivandeTextRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareIdRepository;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.model.*;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;

import java.util.ArrayList;
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
    private MedgivandeTextRepository medgivandeTextRepository;

    @Autowired
    private PrivatlakareIdRepository privatlakareidRepository;

    @Autowired
    private HospPersonService hospPersonService;

    @Autowired
    private HospUpdateService hospUpdateService;

    @Override
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

    @Override
    @Transactional
    public RegistrationWithHospInformation getRegistration() {
        Privatlakare privatlakare = privatlakareRepository.findByPersonId(userService.getUser().getPersonalIdentityNumber());

        if (privatlakare == null) {
            return new RegistrationWithHospInformation(null, null);
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

        HospInformation hospInformation = new HospInformation();
        List<String> legitimeradeYrkesgrupper = new ArrayList<String>();
        for(LegitimeradYrkesgrupp legitimeradYrkesgrupp : privatlakare.getLegitimeradeYrkesgrupper()) {
            legitimeradeYrkesgrupper.add(legitimeradYrkesgrupp.getNamn());
        }
        if (!legitimeradeYrkesgrupper.isEmpty()) {
            hospInformation.setHsaTitles(legitimeradeYrkesgrupper);
        }

        List<String> specialiteter = new ArrayList<String>();
        for(Specialitet specialitet : privatlakare.getSpecialiteter()) {
            specialiteter.add(specialitet.getNamn());
        }
        if (!specialiteter.isEmpty()) {
            hospInformation.setSpecialityNames(specialiteter);
        }

        hospInformation.setPersonalPrescriptionCode(privatlakare.getForskrivarKod());

        return new RegistrationWithHospInformation(registration, hospInformation);
    }

    @Override
    @Transactional
    public RegistrationStatus createRegistration(Registration registration, Long godkantMedgivandeVersion) {

        if (registration == null || !registration.checkIsValid()) {
            LOG.error("createRegistration: CreateRegistrationRequest is not valid");
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.BAD_REQUEST,
                    "CreateRegistrationRequest is not valid");
        }

        if (godkantMedgivandeVersion == null || godkantMedgivandeVersion <= 0) {
            LOG.error("createRegistration: Not allowed to create registration without medgivande");
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.BAD_REQUEST,
                    "Not allowed to create registration without medgivande");
        }

        if (privatlakareRepository.findByPersonId(userService.getUser().getPersonalIdentityNumber()) != null) {
            LOG.error("createRegistration: Registration already exists");
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.ALREADY_EXISTS,
                    "Registration already exists");
        }
        
        if (!userService.getUser().isNameFromPuService()) {
            LOG.error("createRegistration: Not allowed to create registration without updated name from PU-service");
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.UNKNOWN_INTERNAL_PROBLEM,
                    "Not allowed to create registration without updated name from PU-service");
        }

        Privatlakare privatlakare = new Privatlakare();

        MedgivandeText medgivandeText = medgivandeTextRepository.findOne(godkantMedgivandeVersion);
        if (medgivandeText == null) {
            LOG.error("createRegistration: Could not find medgivandetext with version '{}'", godkantMedgivandeVersion);
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.BAD_REQUEST,
                    "Could not find medgivandetext matching godkantMedgivandeVersion");
        }
        Medgivande medgivande = new Medgivande();
        medgivande.setGodkandDatum(LocalDateTime.now());
        medgivande.setMedgivandeText(medgivandeText);
        medgivande.setPrivatlakare(privatlakare);
        Set<Medgivande> medgivandeSet = new HashSet<>();
        privatlakare.setMedgivande(medgivandeSet);

        privatlakare.setPersonId(userService.getUser().getPersonalIdentityNumber());
        privatlakare.setFullstandigtNamn(userService.getUser().getName());
        privatlakare.setGodkandAnvandare(true);

        // Generate next hsaId
        // Format: "SE" + ineras orgnr (inkl "sekelsiffror", alltså 165565594230) + "-" + "WEBCERT" + femsiffrigt löpnr.
        // PrivatlakareId uses an autoincrement column to get next value
        PrivatlakareId privatlakareId = privatlakareidRepository.save(new PrivatlakareId());
        String hsaId = "SE165565594230-WEBCERT" + StringUtils.leftPad(Integer.toString(privatlakareId.getId()), 5, '0');

        privatlakare.setEnhetsId(hsaId);
        privatlakare.setHsaId(hsaId);
        privatlakare.setVardgivareId(hsaId);

        // Set properties from client
        convertRegistrationToPrivatlakare(registration, privatlakare);

        // Lookup hospPerson in HSA
        RegistrationStatus status = hospUpdateService.updateHospInformation(privatlakare, true);

        privatlakareRepository.save(privatlakare);

        return status;
    }

    @Override
    public SaveRegistrationResponseStatus saveRegistration(Registration registration) {
        if (registration == null || !registration.checkIsValid()) {
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.BAD_REQUEST,
                    "SaveRegistrationRequest is not valid");
        }

        Privatlakare privatlakare = privatlakareRepository.findByPersonId(userService.getUser().getPersonalIdentityNumber());

        if (privatlakare == null) {
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.NOT_FOUND,
                    "Registration not found");
        }

        convertRegistrationToPrivatlakare(registration, privatlakare);

        privatlakareRepository.save(privatlakare);

        return SaveRegistrationResponseStatus.OK;
    }

    @Override
    public boolean removePrivatlakare(String personId) {
        Privatlakare toDelete = privatlakareRepository.findByPersonId(personId);
        if (toDelete == null) {
            LOG.error("No Privatlakare with id {} found in the database!", personId);
            return false;
        }
        privatlakareRepository.delete(toDelete);
        LOG.info("Deleted Privatlakare with id {}", personId);
        return true;
    }

    private void convertRegistrationToPrivatlakare(Registration registration, Privatlakare privatlakare) {
        privatlakare.setAgarform("Privat");
        privatlakare.setArbetsplatsKod(registration.getArbetsplatskod());
        privatlakare.setEnhetsNamn(registration.getVerksamhetensNamn());
        privatlakare.setEpost(registration.getEpost());
        privatlakare.setKommun(registration.getKommun());
        privatlakare.setLan(registration.getLan());
        privatlakare.setPostadress(registration.getAdress());
        privatlakare.setPostnummer(registration.getPostnummer());
        privatlakare.setPostort(registration.getPostort());
        privatlakare.setTelefonnummer(registration.getTelefonnummer());
        privatlakare.setVardgivareNamn(registration.getVerksamhetensNamn());

        /* Effectively change the oneToMany cardinality of the following to act as oneToOne, see javadoc for more info*/
        privatlakare.updateBefattningar(registration.getBefattning());
        privatlakare.updateVardformer(registration.getVardform());
        privatlakare.updateVerksamhetstyper(registration.getVerksamhetstyp());
    }

}
