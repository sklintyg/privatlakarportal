package se.inera.privatlakarportal.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
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
import java.util.Set;

/**
 * Created by pebe on 2015-06-26.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private PrivatlakareRepository privatlakareRepository;

    @Autowired
    private PrivatlakareIdRepository privatlakareidRepository;

    @Autowired
    private HospPersonService hospPersonService;

    @Override
    @Transactional
    public Registration getRegistration() {
        // TODO Get personId and name from logged in user object
        Privatlakare privatlakare = privatlakareRepository.findByPersonId("19121212-1212");

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
        // TODO Get personId from logged in user object
        GetHospPersonResponseType response = hospPersonService.getHospPerson("19121212-1212");

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

        if (privatlakareRepository.findByPersonId("19121212-1212") != null) {
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.BAD_REQUEST,
                    "Registration already exists");
        }

        // Generate next hsaId
        // Format: "SE" + ineras orgnr (inkl "sekelsiffror", alltså 165565594230) + "-" + "WEBCERT" + femsiffrigt löpnr.
        // PrivatlakareId uses an autoincrement column to get next value
        PrivatlakareId privatlakareId = privatlakareidRepository.save(new PrivatlakareId());
        String hsaId = "SE165565594230-WEBCERT" + StringUtils.leftPad(Integer.toString(privatlakareId.getId()), 5, '0');

        Privatlakare privatlakare = new Privatlakare();

        // TODO Get personId and name from logged in user object
        privatlakare.setPersonId("19121212-1212");
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
        privatlakare.setTelefonnummer(registration.getEpost());
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

        // TODO Get following information from HSA
//        privatlakare.setSpecialiteter());
//        privatlakare.setLegitimeradeYrkesgrupper();
//        privatlakare.setForskrivarKod();
//        privatlakare.setGodkandAnvandare(true);

        privatlakareRepository.save(privatlakare);

        return CreateRegistrationResponseStatus.AUTHORIZED;
    }
}
