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
package se.inera.intyg.privatlakarportal.pu.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import se.inera.intyg.privatlakarportal.pu.stub.LookupResidentForFullProfileWsStub;
import se.inera.intyg.privatlakarportal.pu.stub.PUBootstrapBean;
import se.inera.intyg.privatlakarportal.pu.stub.ResidentStore;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Profile({"dev", "pu-stub"})
public class PUStubConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    PUBootstrapBean puBootstrap() {
        return new PUBootstrapBean();
    }

    @Bean
    ResidentStore residentStore() {
        return new ResidentStore();
    }

    @Bean
    LookupResidentForFullProfileWsStub lookupResidentForFullProfileWsStub() {
        return new LookupResidentForFullProfileWsStub();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public EndpointImpl puWsResponder() {
        Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
        Object implementor = lookupResidentForFullProfileWsStub();
        EndpointImpl endpoint = new EndpointImpl(bus, implementor);
        endpoint.publish("/pu");
        return endpoint;
    }
}
