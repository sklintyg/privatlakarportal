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
package se.inera.intyg.privatlakarportal.service.postnummer.repo;

import java.io.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import se.inera.intyg.privatlakarportal.service.postnummer.model.Omrade;

/**
 * Created by pebe on 2015-08-12.
 */
@Component
public class PostnummerRepositoryFactory {

    private static final Logger LOG = LoggerFactory.getLogger(PostnummerRepositoryFactory.class);

    @Autowired
    private Environment env;

    @Autowired
    private ResourceLoader resourceLoader;

    public PostnummerRepository createAndInitPostnummerRepository(String postnummerFile) {

        PostnummerRepositoryImpl postnummerRepository = new PostnummerRepositoryImpl();

        populateRepoFromPostnummerFile(postnummerFile, postnummerRepository);

        LOG.info("Created PostnummerRepository containing {} postnummer", postnummerRepository.nbrOfPostnummer());

        return postnummerRepository;

    }

    private void populateRepoFromPostnummerFile(String fileUrl, PostnummerRepositoryImpl postnummerRepository) {

        if (StringUtils.isBlank(fileUrl)) {
            return;
        }

        String fileEncoding = env.getProperty("postnummer.encoding", "ISO-8859-1");

        LOG.debug("Loading postnummer file '{}' using encoding '{}'", fileUrl, fileEncoding);

        try {
            Resource resource = resourceLoader.getResource(fileUrl);

            if (!resource.exists()) {
                LOG.error("Could not load postnummer file since the resource '{}' does not exists", fileUrl);
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), fileEncoding))) {
                while (reader.ready()) {
                    String line = reader.readLine();
                    Omrade omrade = createOmradeFromString(line);
                    postnummerRepository.addPostnummer(omrade.getPostnummer(), omrade);
                }
            }

        } catch (IOException ioe) {
            LOG.error("IOException occured when loading postnummer file '{}'", fileUrl);
            throw new RuntimeException("Error occured when loading postnummer file", ioe);
        }
    }

    Omrade createOmradeFromString(String line) {
        if (StringUtils.isBlank(line)) {
            return null;
        }

        String[] elements = line.split(";");
        // CHECKSTYLE:OFF MagicNumber
        if (elements.length != 7) {
            LOG.error("Unexpected line in postnummer file. Expected 7 elements separated by semicolon");
            return null;
        }

        String postnummer = elements[0];
        String postort = elements[1];
        // String lansKod = elements[2];
        String lan = elements[3];
        // String kommunKod = elements[4];
        String kommun = elements[5];
        // String arKod = elements[6];
        // CHECKSTYLE:ON MagicNumber

        return new Omrade(postnummer, postort, kommun, lan);
    }
}
