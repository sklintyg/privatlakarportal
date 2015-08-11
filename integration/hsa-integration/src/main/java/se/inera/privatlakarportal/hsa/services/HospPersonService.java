package se.inera.privatlakarportal.hsa.services;

import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;

public interface HospPersonService {

    GetHospPersonResponseType getHospPerson(String personHsaId);

}
