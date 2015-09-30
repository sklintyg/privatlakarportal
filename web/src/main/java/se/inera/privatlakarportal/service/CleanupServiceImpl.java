package se.inera.privatlakarportal.service;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.inera.ifv.hsawsresponder.v3.HandleCertifierType;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;
import se.inera.privatlakarportal.common.service.DateHelperService;
import se.inera.privatlakarportal.hsa.services.HospPersonService;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;

import javax.transaction.Transactional;
import javax.xml.ws.WebServiceException;
import java.util.List;

/**
 * Created by pebe on 2015-09-30.
 */
@Service
public class CleanupServiceImpl implements CleanupService {

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
    public void cleanupPrivatlakare() {

        LOG.debug("Starting scheduled cleanupPrivatlakare");

        LocalDateTime date = dateHelperService.now().minusMonths(12);

        LOG.debug("Checking for privatlakare registered before '{}' still waiting for hosp", date.toString());

        List<Privatlakare> privatlakareList = privatlakareRepository.findNeverHadLakarBehorighetAndRegisteredBefore(date);
        for (Privatlakare privatlakare : privatlakareList) {

            boolean hsaError = false;
            try {
                // Delete from HSA certifier
                if (!hospPersonService.removeFromCertifier(privatlakare.getPersonId(), privatlakare.getHsaId(), "Inte kunnat verifiera läkarbehörighet på 12 månader")) {
                    hsaError = true;
                }
            }
            catch(WebServiceException e) {
                hsaError = true;
            }

            if (!hsaError) {
                privatlakareRepository.delete(privatlakare);
                LOG.info("Cleanup deleted privatlakare '{}' registered on '{}'", privatlakare.getPrivatlakareId(), privatlakare.getRegistreringsdatum().toString());
            }
            else {
                LOG.warn("Failed to remove from HSA certifier while cleanup tried to delete privatlakare '{}'. This operation will be retried during next cleanup cycle", privatlakare.getPrivatlakareId());
            }

        }
    }
}
