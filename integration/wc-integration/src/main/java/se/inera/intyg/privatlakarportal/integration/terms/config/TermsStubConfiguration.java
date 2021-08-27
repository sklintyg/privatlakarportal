/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.integration.terms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.inera.intyg.privatlakarportal.integration.terms.stub.TermsRestStub;
import se.inera.intyg.privatlakarportal.integration.terms.stub.TermsWebServiceStub;

@Configuration
@ComponentScan(basePackages = "se.inera.intyg.privatlakarportal.integration.terms.stub")
@Profile({"dev", "wc-stub"})
public class TermsStubConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TermsRestStub termsRestStub;

    @Bean
    TermsWebServiceStub termsWebServiceStub() {
        return new TermsWebServiceStub();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public EndpointImpl termsWsResponder() {
        Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
        Object implementor = termsWebServiceStub();
        EndpointImpl endpoint = new EndpointImpl(bus, implementor);
        endpoint.publish("/stubs/get-private-practitioner-terms/v1.0");
        return endpoint;
    }

    @Bean
    public Server server() {
        List<JacksonJsonProvider> providers = new ArrayList<>();
        providers.add(getJsonProvider());

        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setProviders(providers);
        endpoint.setBus(springBus());
        endpoint.setAddress("/stubs");
        endpoint.setServiceBeans(Arrays.asList(termsRestStub));

        return endpoint.create();
    }

    @Bean
    public JacksonJsonProvider getJsonProvider() {
        return new JacksonJsonProvider();
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

}
