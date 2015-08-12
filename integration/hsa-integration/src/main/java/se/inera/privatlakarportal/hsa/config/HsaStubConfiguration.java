package se.inera.privatlakarportal.hsa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.inera.privatlakarportal.hsa.stub.BootstrapBean;
import se.inera.privatlakarportal.hsa.stub.HsaServiceStub;
import se.inera.privatlakarportal.hsa.stub.HsaWebServiceStub;

@Configuration
@Profile({"dev", "hsa-stub"})
public class HsaStubConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(HsaStubConfiguration.class);

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