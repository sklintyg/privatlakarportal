package se.inera.privatlakarportal.hsa.services;

import org.joda.time.LocalDateTime;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;

public interface HospPersonService {

    boolean handleCertifier(String personId, String certifierId);

    GetHospPersonResponseType getHospPerson(String personId);

    LocalDateTime getHospLastUpdate();

}
