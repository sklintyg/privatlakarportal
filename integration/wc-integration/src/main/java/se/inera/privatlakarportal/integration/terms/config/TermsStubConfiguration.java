package se.inera.privatlakarportal.integration.terms.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.inera.privatlakarportal.integration.terms.stub.TermsWebServiceStub;

@Configuration
@Profile({"dev"})
public class TermsStubConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(TermsStubConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

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
        endpoint.publish("/terms");
        return endpoint;
    }
}
