package se.inera.intyg.privatlakarportal.web.integration.stub;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import se.inera.intyg.privatlakarportal.web.integration.stub.model.CareProviderStub;
import se.inera.intyg.privatlakarportal.web.integration.stub.model.CareUnitStub;
import se.inera.intyg.privatlakarportal.web.integration.stub.model.CredentialInformation;
import se.inera.intyg.privatlakarportal.web.integration.stub.model.HsaPerson;
import se.inera.intyg.privatlakarportal.web.integration.stub.model.SubUnitStub;

@Service
public class HsaServiceStub {


    private List<String> readOnlyCareProvider = new ArrayList<>();

    private Map<String, HsaPerson> hsaPersonMap = new HashMap<>();
    private Map<String, CredentialInformation> credentialInformationMap = new HashMap<>();
    private Map<String, CareProviderStub> careProviderMap = new HashMap<>();
    private Map<String, CareUnitStub> careUnitMap = new HashMap<>();
    private Map<String, SubUnitStub> subUnitMap = new HashMap<>();

    private LocalDateTime lastHospUpdate = LocalDateTime.now(ZoneId.systemDefault());

    public void addCredentialInformation(CredentialInformation credentialInformation) {
        add(credentialInformation.getHsaId(), credentialInformation, credentialInformationMap);
    }

    public void updateCredentialInformation(CredentialInformation credentialInformation) {
        CredentialInformation information = get(credentialInformation.getHsaId(), credentialInformationMap);
        if (information != null) {
            information.getCommissionList().addAll(credentialInformation.getCommissionList());
        } else {
            addCredentialInformation(credentialInformation);
        }

    }

    public void deleteCredentialInformation(String hsaId) {
        remove(hsaId, credentialInformationMap);
    }

    public Collection<CredentialInformation> getCredentialInformation() {
        return credentialInformationMap.values();
    }

    public CredentialInformation getCredentialInformation(String hsaId) {
        return get(hsaId, credentialInformationMap);
    }

    public void addHsaPerson(HsaPerson hsaPerson) {
        add(hsaPerson.getHsaId(), hsaPerson, hsaPersonMap);
        add(hsaPerson.getPersonalIdentityNumber(), hsaPerson, hsaPersonMap);
    }

    public void deleteHsaPerson(String id) {
        remove(id, hsaPersonMap);
    }

    public Collection<HsaPerson> getHsaPerson() {
        return hsaPersonMap.values();
    }

    public HsaPerson getHsaPerson(String id) {
        return get(id, hsaPersonMap);
    }

    public void addCareProvider(CareProviderStub careProviderStub) {
        if (careProviderStub != null) {
            add(careProviderStub.getId(), careProviderStub, careProviderMap);

            if (careProviderStub.getCareUnits() != null && !careProviderStub.getCareUnits().isEmpty()) {
                for (CareUnitStub careUnitStub : careProviderStub.getCareUnits()) {
                    careUnitStub.setCareProviderHsaId(careProviderStub.getId());
                    add(careUnitStub.getId(), careUnitStub, careUnitMap);

                    if (careUnitStub.getSubUnits() != null && !careUnitStub.getSubUnits().isEmpty()) {
                        for (SubUnitStub subUnit : careUnitStub.getSubUnits()) {
                            subUnit.setParentHsaId(careUnitStub.getId());
                            add(subUnit.getId(), subUnit, subUnitMap);
                        }
                    }
                }
            }
        }
    }

    public void addCareUnit(CareUnitStub careUnitStub) {
        add(careUnitStub.getId(), careUnitStub, careUnitMap);
    }

    public void deleteCareUnit(String id) {
        remove(id, careUnitMap);
    }

    public void addSubUnit(SubUnitStub subUnitStub) {
        add(subUnitStub.getId(), subUnitStub, subUnitMap);
    }

    public void deleteSubUnit(String id) {
        remove(id, subUnitMap);
    }

    public void deleteCareProvider(String hsaId) {
        var careProvider = get(hsaId, careProviderMap);

        if (careProvider != null) {
            var careUnits = careProvider.getCareUnits();

            if (careUnits != null) {
                for (CareUnitStub careUnitStub : careUnits) {
                    var subUnits = careUnitStub.getSubUnits();

                    if (subUnits != null) {
                        for (SubUnitStub subUnit : subUnits) {
                            remove(subUnit.getId(), subUnitMap);
                        }
                    }
                    remove(careUnitStub.getId(), careUnitMap);
                }
            }
            remove(hsaId, careProviderMap);
        }
    }

    public Collection<CareProviderStub> getCareProvider() {
        return careProviderMap.values();
    }

    public CareProviderStub getCareProvider(String hsaId) {
        return get(hsaId, careProviderMap);

    }

    public CareUnitStub getCareUnit(String hsaId) {
        return get(hsaId, careUnitMap);

    }

    public SubUnitStub getSubUnit(String hsaId) {
        return get(hsaId, subUnitMap);

    }

    public void markAsReadOnly(String hsaId) {
        if (hsaId != null) {
            readOnlyCareProvider.add(hsaId.toUpperCase());
        }
    }

    public boolean isCareProviderReadOnly(String hsaId) {
        return hsaId != null && readOnlyCareProvider.contains(hsaId.toUpperCase());
    }

    public LocalDateTime getHospLastUpdate() {
        return lastHospUpdate;
    }

    public void resetHospLastUpdate() {
        lastHospUpdate = LocalDateTime.now(ZoneId.systemDefault());
    }

    private static <T> void add(String id, T value, Map<String, T> map) {
        if (id != null && value != null && map != null) {
            map.put(formatId(id), value);
        }
    }

    private static <T> void remove(String id, Map<String, T> map) {
        if (id != null && map != null) {
            map.remove(formatId(id));
        }
    }

    private static <T> T get(String id, Map<String, T> map) {
        if (id != null && map != null) {
            var formatId = formatId(id);
            return isNullOrShouldNotExistInHsa(formatId) ? null : map.get(formatId);
        }
        return null;

    }

    private static String formatId(String id) {
        return StringUtils.trimAllWhitespace(id.toUpperCase());
    }

    private static boolean isNullOrShouldNotExistInHsa(String hsaId) {
        return hsaId == null || hsaId.startsWith("EJHSA") || "UTANENHETSID".equals(hsaId) || hsaId.endsWith("-FINNS-EJ");
    }

}