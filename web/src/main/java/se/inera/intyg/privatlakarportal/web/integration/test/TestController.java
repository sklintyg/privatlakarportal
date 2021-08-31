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
package se.inera.intyg.privatlakarportal.web.integration.test;

import io.swagger.annotations.Api;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.infra.integration.hsatk.stub.HsaServiceStub;
import se.inera.intyg.infra.integration.hsatk.stub.model.HsaPerson;
import se.inera.intyg.privatepractitioner.dto.ValidatePrivatePractitionerResponse;
import se.inera.intyg.privatlakarportal.hsa.services.HospUpdateService;
import se.inera.intyg.privatlakarportal.integration.privatepractitioner.services.IntegrationService;
import se.inera.intyg.privatlakarportal.persistence.model.HospUppdatering;
import se.inera.intyg.privatlakarportal.persistence.model.Privatlakare;
import se.inera.intyg.privatlakarportal.persistence.repository.HospUppdateringRepository;
import se.inera.intyg.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.intyg.privatlakarportal.service.RegisterService;
import se.inera.intyg.privatlakarportal.web.integration.test.dto.PrivatlakareDto;

/**
 * Created by pebe on 2015-09-02.
 */
@Api(value = "/test", description = "Rest-api för test-tjänster.", produces = MediaType.APPLICATION_JSON, tags = "testability, test")
@RestController
@RequestMapping("/api/test")
@Profile({"dev", "testability-api"})
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
    private static final int YEARS_BACK_IN_TIME = 10;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private PrivatlakareRepository privatlakareRepository;

    @Autowired
    private HospUppdateringRepository hospUppdateringRepository;

    @Autowired(required = false)
    private HsaServiceStub hsaServiceStub;

    @Autowired
    private HospUpdateService hospUpdateService;

    @Autowired
    private IntegrationService integrationService;

    public TestController() {
        LOG.error("testability-api enabled. DO NOT USE IN PRODUCTION");
    }

    @RequestMapping(value = "/registration/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PrivatlakareDto getPrivatlakare(@PathVariable("id") String personId) {
        return registerService.getPrivatlakare(personId);
    }

    @RequestMapping(value = "/registration/remove/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean removePrivatlakare(@PathVariable("id") String personId) {
        return registerService.removePrivatlakare(personId);
    }

    @RequestMapping(value = "/registration/setname/{id}", method = RequestMethod.POST)
    @Transactional(transactionManager = "transactionManager")
    public boolean setNamePrivatlakare(@PathVariable("id") String personId, @RequestBody String name) {
        Privatlakare privatlakare = privatlakareRepository.findByPersonId(personId);
        if (privatlakare == null) {
            LOG.error("Unable to find privatlakare with personId '{}'", personId);
            return false;
        }
        privatlakare.setFullstandigtNamn(name);
        privatlakareRepository.save(privatlakare);
        return true;
    }

    @RequestMapping(value = "/registration/setregistrationdate/{id}", method = RequestMethod.POST)
    @Transactional(transactionManager = "transactionManager")
    public boolean setRegistrationDatePrivatlakare(@PathVariable("id") String personId, @RequestBody String date) {
        Privatlakare privatlakare = privatlakareRepository.findByPersonId(personId);
        if (privatlakare == null) {
            LOG.error("Unable to find privatlakare with personId '{}'", personId);
            return false;
        }
        privatlakare.setRegistreringsdatum(LocalDate.parse(date).atStartOfDay());
        privatlakareRepository.save(privatlakare);
        return true;
    }

    @Profile("hsa-stub")
    @RequestMapping(value = "/hosp/add", method = RequestMethod.POST)
    public void addHospPerson(@RequestBody HsaPerson hsaPerson) {
        hsaServiceStub.addHsaPerson(hsaPerson);
    }

    @Profile("hsa-stub")
    @RequestMapping(value = "/hosp/remove/{id}", method = RequestMethod.DELETE)
    public void removeHospPerson(@PathVariable("id") String id) {
        hsaServiceStub.deleteHsaPerson(id);
    }

    @RequestMapping(value = "/hosp/update", method = RequestMethod.POST)
    @Transactional(transactionManager = "transactionManager")
    public void updateHospInformation() {
        if (hsaServiceStub != null) {
            hsaServiceStub.resetHospLastUpdate();
        }
        HospUppdatering hospUppdatering = hospUppdateringRepository.findSingle();
        // Save hosp update time in database
        if (hospUppdatering == null) {
            hospUppdatering = new HospUppdatering(LocalDateTime.now().minusYears(YEARS_BACK_IN_TIME));
        } else {
            hospUppdatering.setSenasteHospUppdatering(LocalDateTime.now().minusYears(YEARS_BACK_IN_TIME));
        }

        hospUppdateringRepository.save(hospUppdatering);
        hospUpdateService.resetTimer();
        hospUpdateService.updateHospInformation();
    }

    @Profile("hsa-stub")
    @RequestMapping(value = "/hosp/reset", method = RequestMethod.GET)
    public void resetHospUpdateTimers() {
        hsaServiceStub.resetHospLastUpdate();
    }

    @RequestMapping(value = "/webcert/validatePrivatePractitioner/{id}", method = RequestMethod.POST)
    public ValidatePrivatePractitionerResponse validatePrivatePractitioner(@PathVariable("id") String id) {
        return integrationService.validatePrivatePractitionerByPersonId(id);
    }
}
