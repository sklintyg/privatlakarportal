package se.inera.privatlakarportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.inera.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.privatlakarportal.integration.terms.services.dto.Terms;
import se.inera.privatlakarportal.persistence.model.MedgivandeText;
import se.inera.privatlakarportal.persistence.repository.MedgivandeTextRepository;

/**
 * Created by pebe on 2015-09-09.
 */
@Service
public class TermsServiceImpl implements TermsService {

    private static final Logger LOG = LoggerFactory.getLogger(TermsServiceImpl.class);

    @Autowired
    MedgivandeTextRepository medgivandeTextRepository;

    @Override
    public Terms getTerms() {
        MedgivandeText medgivandeText = medgivandeTextRepository.findLatest();
        if (medgivandeText == null) {
            LOG.error("getTerms: Could not find medgivandetext");
            throw new PrivatlakarportalServiceException(
                    PrivatlakarportalErrorCodeEnum.BAD_REQUEST,
                    "Could not find medgivandetext");
        }
        return new Terms(medgivandeText.getMedgivandeText(), medgivandeText.getVersion(), medgivandeText.getDatum());
    }
}
