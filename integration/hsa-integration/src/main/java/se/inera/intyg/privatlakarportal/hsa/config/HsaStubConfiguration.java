/*
 * Copyright (C) 2017 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.hsa.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import se.inera.intyg.privatlakarportal.hsa.stub.BootstrapBean;
import se.inera.intyg.privatlakarportal.hsa.stub.HsaServiceStub;
import se.inera.intyg.privatlakarportal.hsa.stub.HsaWebServiceStub;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan({"se.inera.intyg.privatlakarportal.common.config"})
@Profile({"dev", "hsa-stub"})
public class HsaStubConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    BootstrapBean bootstrap() {
        return new BootstrapBean();
    }

    @Bean
    HsaServiceStub hsaServiceStub() {
        return new HsaServiceStub();
    }

    @Bean
    HsaWebServiceStub hsaWebServiceStub() {
        return new HsaWebServiceStub();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public EndpointImpl hsaWsResponder() {
        Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
        Object implementor = hsaWebServiceStub();
        EndpointImpl endpoint = new EndpointImpl(bus, implementor);
        endpoint.publish("/hsa");
        return endpoint;
    }
}
