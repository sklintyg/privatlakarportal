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
package se.inera.intyg.privatlakarportal.hsa.stub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BootstrapBean {
    private static final Logger LOG = LoggerFactory.getLogger(BootstrapBean.class);

    @Autowired
    private HsaServiceStub hsaServiceStub;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void bootstrapHospPerson() throws IOException {
        List<Resource> files = getResourceListing("classpath:bootstrap-hospperson/*.json");
        LOG.info("Bootstrapping {} hospPerson for HSA stub ...", files.size());
        for (Resource res : files) {
            addHospPerson(res);
        }
    }

    private List<Resource> getResourceListing(String classpathResourcePath) {
        try {
            PathMatchingResourcePatternResolver r = new PathMatchingResourcePatternResolver();
            return Arrays.asList(r.getResources(classpathResourcePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addHospPerson(Resource res) throws IOException {
        HsaHospPerson hospPerson = objectMapper.readValue(res.getFile(), HsaHospPerson.class);
        hsaServiceStub.addHospPerson(hospPerson);
        LOG.info("Loaded hospPerson " + hospPerson.getPersonalIdentityNumber());
    }
}