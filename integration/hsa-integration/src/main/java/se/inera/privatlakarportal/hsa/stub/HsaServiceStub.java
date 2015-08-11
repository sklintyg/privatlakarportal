package se.inera.privatlakarportal.hsa.stub;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HsaServiceStub {

    private Map<String, HsaHospPerson> personMap = new HashMap<String, HsaHospPerson>();

    public HsaHospPerson getHospPerson(String personId) {
        return personMap.get(personId);
    }

    public void addHospPerson(HsaHospPerson hospPerson) {
        personMap.put(hospPerson.getPersonalIdentityNumber(), hospPerson);
    }
}
