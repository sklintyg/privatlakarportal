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
package se.inera.intyg.privatlakarportal.service.postnummer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import se.inera.intyg.privatlakarportal.service.postnummer.model.Omrade;
import se.inera.intyg.privatlakarportal.service.postnummer.repo.PostnummerRepository;
import se.inera.intyg.privatlakarportal.service.postnummer.repo.PostnummerRepositoryFactory;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by pebe on 2015-08-12.
 */
@Service
public class PostnummerServiceImpl implements PostnummerService {

    private static final Logger LOG = LoggerFactory.getLogger(PostnummerRepositoryFactory.class);

    @Autowired
    private Environment env;

    @Autowired
    private PostnummerRepositoryFactory postnummerRepositoryFactory;

    private PostnummerRepository postnummerRepository;

    @PostConstruct
    public void init() {
        postnummerRepository = postnummerRepositoryFactory.createAndInitPostnummerRepository(env.getProperty("postnummer.file"));
    }

    @Override
    public List<Omrade> getOmradeByPostnummer(String postnummer) {
        LOG.debug("Lookup omrade by postnummer '{}'", postnummer);
        return postnummerRepository.getOmradeByPostnummer(postnummer);
    }

}
