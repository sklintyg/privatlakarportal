package se.inera.privatlakarportal.persistence.repository.util;

import java.time.LocalDate;
import java.util.*;

import se.inera.privatlakarportal.persistence.model.*;

public class PrivatelakareTestUtil {

    public static Privatlakare buildPrivatlakare(String personId, int hsaCounter, boolean isLakare) {
        return buildPrivatlakare(
            personId,
            "SE000000000000-WEBCERT0000"+hsaCounter,
            "Tolvan Tolvansson",
            "test@example.com",
            "4444444444",
            "postadress",
            "postnummer",
            "postort",
            "2015-08-01",
            isLakare);
    }

    public static Privatlakare buildPrivatlakare(
            String personId, String hsaId, String fullstandigtNamn, String epost, String telefonnummer,
            String postadress, String postnummer, String postort, String registreringsdatum, boolean isLakare) {
        Privatlakare privatlakare = new Privatlakare();

        privatlakare.setPersonId(personId);
        privatlakare.setHsaId(hsaId);
        privatlakare.setFullstandigtNamn(fullstandigtNamn);
        privatlakare.setEpost(epost);
        privatlakare.setTelefonnummer(telefonnummer);
        privatlakare.setPostadress(postadress);
        privatlakare.setPostnummer(postnummer);
        privatlakare.setPostort(postort);
        privatlakare.setForskrivarKod("0000000");

        privatlakare.setAgarform("Privat");
        privatlakare.setGodkandAnvandare(true);
        privatlakare.setArbetsplatsKod("0000000000");

        privatlakare.setLan("Län");
        privatlakare.setKommun("Kommun");

        privatlakare.setEnhetsId(hsaId);
        privatlakare.setEnhetsNamn("Enhetsnamn");
        if (isLakare) {
            privatlakare.setEnhetStartdatum(LocalDate.parse("2015-06-01").atStartOfDay());
            privatlakare.setEnhetSlutDatum(LocalDate.parse("2015-06-02").atStartOfDay());
        }

        privatlakare.setVardgivareId(hsaId);
        privatlakare.setVardgivareNamn("Vardgivarnamn");
        if (isLakare) {
            privatlakare.setVardgivareStartdatum(LocalDate.parse("2015-06-03").atStartOfDay());
            privatlakare.setVardgivareSlutdatum(LocalDate.parse("2015-06-04").atStartOfDay());
        }

        Set<Befattning> befattningar = new HashSet<>();
        befattningar.add(new Befattning(privatlakare, "Befattning kod 1"));
        befattningar.add(new Befattning(privatlakare, "Befattning kod 2"));
        privatlakare.setBefattningar(befattningar);

        Set<LegitimeradYrkesgrupp> legitimeradeYrkesgrupper = new HashSet<>();
        if (isLakare) {
            legitimeradeYrkesgrupper.add(new LegitimeradYrkesgrupp(privatlakare, "Läkare", "LK"));
        }
        privatlakare.setLegitimeradeYrkesgrupper(legitimeradeYrkesgrupper);

        List<Specialitet> specialiteter = new ArrayList<>();
        specialiteter.add(new Specialitet(privatlakare, "Specialitet kod 1", "Specialitet namn 1"));
        specialiteter.add(new Specialitet(privatlakare, "Specialitet kod 2", "Specialitet namn 2"));
        privatlakare.setSpecialiteter(specialiteter);

        Set<Verksamhetstyp> verksamhetsTyper = new HashSet<>();
        verksamhetsTyper.add(new Verksamhetstyp(privatlakare, "Verksamhetstyp 1"));
        verksamhetsTyper.add(new Verksamhetstyp(privatlakare, "Verksamhetstyp 2"));
        privatlakare.setVerksamhetstyper(verksamhetsTyper);

        Set<Vardform> vardformer = new HashSet<>();
        vardformer.add(new Vardform(privatlakare, "Vardform 1"));
        vardformer.add(new Vardform(privatlakare, "Vardform 2"));
        privatlakare.setVardformer(vardformer);

        privatlakare.setRegistreringsdatum(LocalDate.parse(registreringsdatum).atStartOfDay());

        return privatlakare;
    }



}
