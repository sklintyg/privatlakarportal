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
package se.inera.intyg.privatlakarportal.config;

// CHECKSTYLE:OFF LineLength

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.support.ServletContextAttributeExporter;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import se.inera.intyg.privatlakarportal.service.monitoring.HealthCheckService;
import se.inera.intyg.privatlakarportal.service.monitoring.PingForConfigurationResponderImpl;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionerterms.v1.rivtabp21.GetPrivatePractitionerTermsResponderInterface;

// CHECKSTYLE:ON LineLength

/**
 * Created by pebe on 2015-09-07.
 */
@Configuration
@ComponentScan("se.inera.intyg.privatlakarportal.service, se.inera.intyg.privatlakarportal.common.service")
@EnableScheduling
public class ServiceConfig {
    @Autowired
    HealthCheckService healtCheckService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Bus cxfBus;

    @Value("${terms.ws.services.url}")
    private String termsUrl;

    @Bean
    public ServletContextAttributeExporter contextAttributes() {
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("healthcheck", healtCheckService);
        final ServletContextAttributeExporter exporter = new ServletContextAttributeExporter();
        exporter.setAttributes(attributes);
        return exporter;
    }

    @Bean
    public JacksonJsonProvider jacksonJsonProvider() {
        return new JacksonJsonProvider();
    }

    @Bean
    public PingForConfigurationResponderImpl pingForConfigurationResponder() {
        return new PingForConfigurationResponderImpl();
    }

    @Bean
    public EndpointImpl pingForConfigurationEndpoint() {
        Object implementor = pingForConfigurationResponder();
        EndpointImpl endpoint = new EndpointImpl(cxfBus, implementor);
        endpoint.publish("/ping-for-configuration");
        return endpoint;
    }

    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        return loggingFeature;
    }

    @Bean
    public GetPrivatePractitionerTermsResponderInterface termsWebServiceClient() {
        JaxWsProxyFactoryBean proxyFactoryBean = new JaxWsProxyFactoryBean();
        proxyFactoryBean.setServiceClass(GetPrivatePractitionerTermsResponderInterface.class);
        proxyFactoryBean.setAddress(termsUrl);
        return (GetPrivatePractitionerTermsResponderInterface) proxyFactoryBean.create();
    }
}
