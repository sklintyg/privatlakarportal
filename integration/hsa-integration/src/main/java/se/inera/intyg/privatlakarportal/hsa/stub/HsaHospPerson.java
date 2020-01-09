/*
 * Copyright (C) 2020 Inera AB (http://www.inera.se)
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HsaHospPerson implements Serializable {

    String personalIdentityNumber;
    String personalPrescriptionCode;
    List<String> educationCodes = new ArrayList<>();
    List<String> restrictions = new ArrayList<>();
    List<String> restrictionCodes = new ArrayList<>();
    List<String> titleCodes = new ArrayList<>();
    List<String> specialityCodes = new ArrayList<>();
    List<String> specialityNames = new ArrayList<>();
    List<String> hsaTitles = new ArrayList<>();

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public void setPersonalIdentityNumber(String personalIdentityNumber) {
        this.personalIdentityNumber = personalIdentityNumber;
    }

    public String getPersonalPrescriptionCode() {
        return personalPrescriptionCode;
    }

    public void setPersonalPrescriptionCode(String personalPrescriptionCode) {
        this.personalPrescriptionCode = personalPrescriptionCode;
    }

    public List<String> getEducationCodes() {
        return educationCodes;
    }

    public void setEducationCodes(List<String> educationCodes) {
        this.educationCodes = educationCodes;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public List<String> getRestrictionCodes() {
        return restrictionCodes;
    }

    public void setRestrictionCodes(List<String> restrictionCodes) {
        this.restrictionCodes = restrictionCodes;
    }

    public List<String> getTitleCodes() {
        return titleCodes;
    }

    public void setTitleCodes(List<String> titleCodes) {
        this.titleCodes = titleCodes;
    }

    public List<String> getSpecialityCodes() {
        return specialityCodes;
    }

    public void setSpecialityCodes(List<String> specialityCodes) {
        this.specialityCodes = specialityCodes;
    }

    public List<String> getSpecialityNames() {
        return specialityNames;
    }

    public void setSpecialityNames(List<String> specialityNames) {
        this.specialityNames = specialityNames;
    }

    public List<String> getHsaTitles() {
        return hsaTitles;
    }

    public void setHsaTitles(List<String> hsaTitles) {
        this.hsaTitles = hsaTitles;
    }
}
