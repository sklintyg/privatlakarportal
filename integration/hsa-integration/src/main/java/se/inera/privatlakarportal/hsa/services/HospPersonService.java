package se.inera.privatlakarportal.hsa.services;

import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.HandleCertifierResponseType;

public interface HospPersonService {

    boolean handleCertifier(String personId, String certifierId);

    GetHospPersonResponseType getHospPerson(String personId);

}
