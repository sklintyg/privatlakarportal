package se.inera.privatlakarportal.integration.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import se.inera.privatlakarportal.integration.services.GetPrivatePractitionerResponderImpl;
import se.inera.privatlakarportal.integration.services.ValidatePrivatePractitionerResponderImpl;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitioner.v1.rivtabp21.GetPrivatePractitionerResponderInterface;
import se.riv.infrastructure.directory.privatepractitioner.validateprivatepractitioner.v1.rivtabp21.ValidatePrivatePractitionerResponderInterface;

@Configuration
@ComponentScan("se.inera.privatlakarportal.integration.services")
public class WcIntegrationConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public GetPrivatePractitionerResponderInterface getPrivatePractitionerResponder() {
        return new GetPrivatePractitionerResponderImpl();
    }

    @Bean
    public ValidatePrivatePractitionerResponderInterface validatePrivatePractitionerResponder() {
        return new ValidatePrivatePractitionerResponderImpl();
    }

    @Bean
    public EndpointImpl getPrivatePractitionerEndpoint() {
        Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
        Object implementor = getPrivatePractitionerResponder();
        EndpointImpl endpoint = new EndpointImpl(bus, implementor);
        endpoint.publish("/get-private-practitioner/v1.0");
        return endpoint;
    }

    @Bean
    public EndpointImpl validatePrivatePractitionerEndpoint() {
        Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
        Object implementor = validatePrivatePractitionerResponder();
        EndpointImpl endpoint = new EndpointImpl(bus, implementor);
        endpoint.publish("/validate-private-practitioner/v1.0");
        return endpoint;
    }
}
