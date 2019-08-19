/*
 * Copyright (C) 2019 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.inera.intyg.privatlakarportal.common.exception.PrivatlakarportalErrorCodeEnum;
import se.inera.intyg.privatlakarportal.common.exception.PrivatlakarportalServiceException;
import se.inera.intyg.privatlakarportal.integration.terms.services.dto.Terms;
import se.inera.intyg.privatlakarportal.persistence.model.MedgivandeText;
import se.inera.intyg.privatlakarportal.persistence.repository.MedgivandeTextRepository;

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
