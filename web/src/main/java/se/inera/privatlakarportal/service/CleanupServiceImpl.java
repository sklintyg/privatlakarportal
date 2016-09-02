/**
 * Copyright (C) 2016 Inera AB (http://www.inera.se)
 *
 * This file is part of privatlakarportal (https://github.com/sklintyg/privatlakarportal).
 *
 * privatlakarportal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * privatlakarportal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.privatlakarportal.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import se.inera.privatlakarportal.common.service.DateHelperService;
import se.inera.privatlakarportal.hsa.services.HospPersonService;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;

/**
 * Created by pebe on 2015-09-30.
 */
@Service
public class CleanupServiceImpl implements CleanupService {

    private static final int MONTHS_BEFORE_REMOVED = 12;

    private static final Logger LOG = LoggerFactory.getLogger(CleanupServiceImpl.class);

    @Autowired
    PrivatlakareRepository privatlakareRepository;

    @Autowired
    DateHelperService dateHelperService;

    @Autowired
    HospPersonService hospPersonService;

    @Override
    @Scheduled(cron = "${privatlakarportal.cleanup.cron}")
    @Transactional
    public void scheduledCleanupPrivatlakare() {
        String skipUpdate = System.getProperty("scheduled.update.skip", "false");
        LOG.debug("scheduled.update.skip = " + skipUpdate);
        if (skipUpdate.equalsIgnoreCase("true")) {
            LOG.info("Skipping scheduled cleanupPrivatlakare");
        } else {
            LOG.info("Starting scheduled cleanupPrivatlakare");
            cleanupPrivatlakare();
        }
    }

    @Override
    public void cleanupPrivatlakare() {
        LocalDateTime date = dateHelperService.now().minusMonths(MONTHS_BEFORE_REMOVED);

        LOG.debug("Checking for privatlakare registered before '{}' still waiting for hosp", date.toString());

        List<Privatlakare> privatlakareList = privatlakareRepository.findNeverHadLakarBehorighetAndRegisteredBefore(date);
        for (Privatlakare privatlakare : privatlakareList) {

            boolean hsaError = false;
            try {
                // Delete from HSA certifier
                if (!hospPersonService.removeFromCertifier(privatlakare.getPersonId(), privatlakare.getHsaId(),
                        "Inte kunnat verifiera läkarbehörighet på 12 månader")) {
                    hsaError = true;
                }
            } catch (WebServiceException e) {
                hsaError = true;
                LOG.error("Error encountered while attempting to contact HSA {}", e);
            }

            if (!hsaError) {
                privatlakareRepository.delete(privatlakare);
                LOG.info("Cleanup deleted privatlakare '{}' registered on '{}'", privatlakare.getPrivatlakareId(), privatlakare
                        .getRegistreringsdatum().toString());
            } else {
                LOG.warn(
                        "Failed to remove from HSA certifier while cleanup tried to delete privatlakare '{}'. This operation will be retried during next cleanup cycle",
                        privatlakare.getPrivatlakareId());
            }

        }
    }
}
