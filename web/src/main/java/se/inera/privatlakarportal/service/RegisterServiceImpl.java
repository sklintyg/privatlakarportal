package se.inera.privatlakarportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.privatlakarportal.persistence.model.Befattning;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.model.Vardform;
import se.inera.privatlakarportal.persistence.model.Verksamhetstyp;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationRequest;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pebe on 2015-06-26.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private PrivatlakareRepository privatlakareRepository;

    @Override
    @Transactional
    public void createRegistration(CreateRegistrationRequest request) {

        Privatlakare privatlakare = new Privatlakare();

        // TODO This id should be generated
        String hsaId = "SE000000000000-WEBCERT00001";

        // TODO Get personId and name from logged in user object
        privatlakare.setPersonId("19121212-1212");
        privatlakare.setFullstandigtNamn("Namn");

        privatlakare.setAgarform("Privat");
        privatlakare.setArbetsplatsKod(request.getArbetsplatskod());
        privatlakare.setEnhetsId(hsaId);
        privatlakare.setEnhetsNamn(request.getVerksamhetensNamn());
        privatlakare.setEpost(request.getEpost());
        privatlakare.setHsaId(hsaId);
        privatlakare.setKommun(request.getKommun());
        privatlakare.setLan(request.getLan());
        privatlakare.setPostadress(request.getAdress());
        privatlakare.setPostnummer(request.getPostnummer());
        privatlakare.setPostort(request.getPostort());
        privatlakare.setTelefonnummer(request.getEpost());
        privatlakare.setVardgivareId(hsaId);
        privatlakare.setVardgivareNamn(request.getVerksamhetensNamn());

        Set<Befattning> befattningar = new HashSet<Befattning>();
        befattningar.add(new Befattning(privatlakare, request.getBefattning()));
        privatlakare.setBefattningar(befattningar);

        Set<Vardform> vardformer = new HashSet<Vardform>();
        vardformer.add(new Vardform(privatlakare, request.getVardform()));
        privatlakare.setVardformer(vardformer);

        Set<Verksamhetstyp> verksamhetstyper = new HashSet<Verksamhetstyp>();
        verksamhetstyper.add(new Verksamhetstyp(privatlakare, request.getVerksamhetstyp()));
        privatlakare.setVerksamhetstyper(verksamhetstyper);

        // Get from HSA
//        privatlakare.setSpecialiteter());
//        privatlakare.setLegitimeradeYrkesgrupper();
//        privatlakare.setForskrivarKod();
//        privatlakare.setGodkandAnvandare();

        privatlakareRepository.save(privatlakare);
    }
}
