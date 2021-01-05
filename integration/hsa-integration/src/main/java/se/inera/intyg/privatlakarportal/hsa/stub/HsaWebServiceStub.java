/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.hsa.stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3.wsaddressing10.AttributedURIType;
import se.inera.ifv.hsaws.v3.HsaWsFault;
import se.inera.ifv.hsaws.v3.HsaWsResponderInterface;
import se.inera.ifv.hsawsresponder.v3.EducationCodesType;
import se.inera.ifv.hsawsresponder.v3.GetCareUnitListResponseType;
import se.inera.ifv.hsawsresponder.v3.GetCareUnitMembersResponseType;
import se.inera.ifv.hsawsresponder.v3.GetCareUnitResponseType;
import se.inera.ifv.hsawsresponder.v3.GetHospLastUpdateResponseType;
import se.inera.ifv.hsawsresponder.v3.GetHospLastUpdateType;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.GetHospPersonType;
import se.inera.ifv.hsawsresponder.v3.GetHsaPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.GetHsaPersonType;
import se.inera.ifv.hsawsresponder.v3.GetHsaUnitResponseType;
import se.inera.ifv.hsawsresponder.v3.GetInformationListResponseType;
import se.inera.ifv.hsawsresponder.v3.GetInformationListType;
import se.inera.ifv.hsawsresponder.v3.GetMiuForPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.GetMiuForPersonType;
import se.inera.ifv.hsawsresponder.v3.GetPriceUnitsForAuthResponseType;
import se.inera.ifv.hsawsresponder.v3.GetPriceUnitsForAuthType;
import se.inera.ifv.hsawsresponder.v3.GetStatisticsCareGiverResponseType;
import se.inera.ifv.hsawsresponder.v3.GetStatisticsCareGiverType;
import se.inera.ifv.hsawsresponder.v3.GetStatisticsHsaUnitResponseType;
import se.inera.ifv.hsawsresponder.v3.GetStatisticsHsaUnitType;
import se.inera.ifv.hsawsresponder.v3.GetStatisticsNamesResponseType;
import se.inera.ifv.hsawsresponder.v3.GetStatisticsNamesType;
import se.inera.ifv.hsawsresponder.v3.GetStatisticsPersonResponseType;
import se.inera.ifv.hsawsresponder.v3.GetStatisticsPersonType;
import se.inera.ifv.hsawsresponder.v3.HandleCertifierResponseType;
import se.inera.ifv.hsawsresponder.v3.HandleCertifierType;
import se.inera.ifv.hsawsresponder.v3.HsaTitlesType;
import se.inera.ifv.hsawsresponder.v3.HsawsSimpleLookupResponseType;
import se.inera.ifv.hsawsresponder.v3.HsawsSimpleLookupType;
import se.inera.ifv.hsawsresponder.v3.IsAuthorizedToSystemResponseType;
import se.inera.ifv.hsawsresponder.v3.IsAuthorizedToSystemType;
import se.inera.ifv.hsawsresponder.v3.LookupHsaObjectType;
import se.inera.ifv.hsawsresponder.v3.PingResponseType;
import se.inera.ifv.hsawsresponder.v3.PingType;
import se.inera.ifv.hsawsresponder.v3.RestrictionCodesType;
import se.inera.ifv.hsawsresponder.v3.RestrictionsType;
import se.inera.ifv.hsawsresponder.v3.SpecialityCodesType;
import se.inera.ifv.hsawsresponder.v3.SpecialityNamesType;
import se.inera.ifv.hsawsresponder.v3.TitleCodesType;
import se.inera.ifv.hsawsresponder.v3.VpwGetPublicUnitsResponseType;
import se.inera.ifv.hsawsresponder.v3.VpwGetPublicUnitsType;

public class HsaWebServiceStub implements HsaWsResponderInterface {

    private static final Logger LOG = LoggerFactory.getLogger(HsaWebServiceStub.class);

    @Autowired
    private HsaServiceStub hsaServiceStub;

    @Override
    public VpwGetPublicUnitsResponseType vpwGetPublicUnits(AttributedURIType logicalAddress, AttributedURIType id,
        VpwGetPublicUnitsType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public GetCareUnitResponseType getCareUnit(AttributedURIType logicalAddress, AttributedURIType id, LookupHsaObjectType parameters)
        throws HsaWsFault {
        return null;
    }

    @Override
    public GetStatisticsPersonResponseType getStatisticsPerson(AttributedURIType logicalAddress, AttributedURIType id,
        GetStatisticsPersonType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public IsAuthorizedToSystemResponseType isAuthorizedToSystem(AttributedURIType logicalAddress, AttributedURIType id,
        IsAuthorizedToSystemType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public GetCareUnitListResponseType getCareUnitList(AttributedURIType logicalAddress, AttributedURIType id,
        LookupHsaObjectType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public GetHospLastUpdateResponseType getHospLastUpdate(AttributedURIType logicalAddress, AttributedURIType id,
        GetHospLastUpdateType parameters) throws HsaWsFault {
        GetHospLastUpdateResponseType response = new GetHospLastUpdateResponseType();
        response.setLastUpdate(hsaServiceStub.getHospLastUpdate());
        return response;
    }

    @Override
    public GetHsaUnitResponseType getHsaUnit(AttributedURIType logicalAddress, AttributedURIType id, LookupHsaObjectType parameters)
        throws HsaWsFault {
        return null;
    }

    @Override
    public GetPriceUnitsForAuthResponseType getPriceUnitsForAuth(AttributedURIType logicalAddress, AttributedURIType id,
        GetPriceUnitsForAuthType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public GetHsaPersonResponseType getHsaPerson(AttributedURIType logicalAddress, AttributedURIType id, GetHsaPersonType parameters)
        throws HsaWsFault {
        return null;
    }

    @Override
    public GetStatisticsNamesResponseType getStatisticsNames(AttributedURIType logicalAddress, AttributedURIType id,
        GetStatisticsNamesType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public PingResponseType ping(AttributedURIType logicalAddress, AttributedURIType id, PingType parameters) throws HsaWsFault {
        PingResponseType response = new PingResponseType();
        response.setMessage("Pong.");
        return response;
    }

    @Override
    public GetMiuForPersonResponseType getMiuForPerson(AttributedURIType logicalAddress, AttributedURIType id,
        GetMiuForPersonType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public GetStatisticsCareGiverResponseType getStatisticsCareGiver(AttributedURIType logicalAddress, AttributedURIType id,
        GetStatisticsCareGiverType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public HsawsSimpleLookupResponseType hsawsSimpleLookup(AttributedURIType logicalAddress, AttributedURIType id,
        HsawsSimpleLookupType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public GetStatisticsHsaUnitResponseType getStatisticsHsaUnit(AttributedURIType logicalAddress, AttributedURIType id,
        GetStatisticsHsaUnitType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public GetCareUnitMembersResponseType getCareUnitMembers(AttributedURIType logicalAddress, AttributedURIType id,
        LookupHsaObjectType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public GetHospPersonResponseType getHospPerson(AttributedURIType logicalAddress, AttributedURIType id, GetHospPersonType parameters)
        throws HsaWsFault {

        String personId = parameters.getPersonalIdentityNumber();
        HsaHospPerson hospPerson = hsaServiceStub.getHospPerson(personId);
        if (hospPerson == null) {
            return null;
        }

        GetHospPersonResponseType response = new GetHospPersonResponseType();
        response.setPersonalIdentityNumber(hospPerson.getPersonalIdentityNumber());
        response.setPersonalPrescriptionCode(hospPerson.getPersonalPrescriptionCode());

        EducationCodesType educationCodes = new EducationCodesType();
        educationCodes.getEducationCode().addAll(hospPerson.getEducationCodes());
        response.setEducationCodes(educationCodes);

        HsaTitlesType hsaTitles = new HsaTitlesType();
        hsaTitles.getHsaTitle().addAll(hospPerson.getHsaTitles());
        response.setHsaTitles(hsaTitles);

        RestrictionCodesType restrictionCodes = new RestrictionCodesType();
        restrictionCodes.getRestrictionCode().addAll(hospPerson.getRestrictionCodes());
        response.setRestrictionCodes(restrictionCodes);

        RestrictionsType restrictions = new RestrictionsType();
        restrictions.getRestriction().addAll(hospPerson.getRestrictions());
        response.setRestrictions(restrictions);

        SpecialityCodesType specialityCodes = new SpecialityCodesType();
        specialityCodes.getSpecialityCode().addAll(hospPerson.getSpecialityCodes());
        response.setSpecialityCodes(specialityCodes);

        SpecialityNamesType specialityNames = new SpecialityNamesType();
        specialityNames.getSpecialityName().addAll(hospPerson.getSpecialityNames());
        response.setSpecialityNames(specialityNames);

        TitleCodesType titleCodes = new TitleCodesType();
        titleCodes.getTitleCode().addAll(hospPerson.getTitleCodes());
        response.setTitleCodes(titleCodes);

        return response;
    }

    @Override
    public GetInformationListResponseType getInformationList(AttributedURIType logicalAddress, AttributedURIType id,
        GetInformationListType parameters) throws HsaWsFault {
        return null;
    }

    @Override
    public HandleCertifierResponseType handleCertifier(AttributedURIType logicalAddress, AttributedURIType id,
        HandleCertifierType parameters) throws HsaWsFault {

        LOG.debug("handleCertifier was called with personId '{}' certifierId '{}'", parameters.getPersonalIdentityNumber(),
            parameters.getCertifierId());

        HandleCertifierResponseType response = new HandleCertifierResponseType();
        response.setResult("OK");
        return response;
    }
}
