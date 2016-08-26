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
package se.inera.privatlakarportal.service.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import org.springframework.stereotype.Service;
import se.inera.privatlakarportal.common.integration.json.CustomObjectMapper;
import se.inera.privatlakarportal.persistence.model.*;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;

@Service
@Profile({"dev", "pp-init-data"})
@DependsOn("dbUpdate")
public class PrivatlakarBootstrapBean {
    private static final Logger LOG = LoggerFactory.getLogger(PrivatlakarBootstrapBean.class);

    @Autowired
    private PrivatlakareRepository privatlakareRepository;

    @PostConstruct
    public void initData() {

        List<Resource> files = getResourceListing("bootstrap-privatlakare/*.json");
        for (Resource res : files) {
            LOG.debug("Loading resource " + res.getFilename());
            addPrivatlakare(res);
        }
    }

    private void addPrivatlakare(Resource res) {

        try {
            Privatlakare privatlakare = new CustomObjectMapper().readValue(res.getInputStream(), Privatlakare.class);
            if (privatlakareRepository.findByPersonId(privatlakare.getPersonId()) == null) {
                for (Befattning befattning : privatlakare.getBefattningar()) {
                    befattning.setPrivatlakare(privatlakare);
                }
                for (LegitimeradYrkesgrupp legitimeradYrkesgrupp : privatlakare.getLegitimeradeYrkesgrupper()) {
                    legitimeradYrkesgrupp.setPrivatlakare(privatlakare);
                }
                for (Specialitet specialitet : privatlakare.getSpecialiteter()) {
                    specialitet.setPrivatlakare(privatlakare);
                }
                for (Verksamhetstyp verksamhetstyp : privatlakare.getVerksamhetstyper()) {
                    verksamhetstyp.setPrivatlakare(privatlakare);
                }
                for (Vardform vardform : privatlakare.getVardformer()) {
                    vardform.setPrivatlakare(privatlakare);
                }
                privatlakareRepository.save(privatlakare);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
}
