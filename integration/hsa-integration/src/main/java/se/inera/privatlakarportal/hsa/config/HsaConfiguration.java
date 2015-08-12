package se.inera.privatlakarportal.hsa.config;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import se.inera.ifv.hsaws.v3.HsaWsResponderInterface;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;

@Configuration
@ComponentScan("se.inera.privatlakarportal.hsa.services")
@Import(HsaStubConfiguration.class)
public class HsaConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(HsaConfiguration.class);


    @Autowired
    private ApplicationContext applicationContext;

    @Value("${hsa.ws.service.logicaladdress}")
    private String hsaLogicalAddress;

    @Value("${hsa.ws.services.url}")
    private String hsaWsUrl;

    @Bean
    public HsaWsResponderInterface hsaWebServiceClient() {
        JaxWsProxyFactoryBean proxyFactoryBean = new JaxWsProxyFactoryBean();
        proxyFactoryBean.setAddress(hsaWsUrl);
        proxyFactoryBean.setServiceClass(HsaWsResponderInterface.class);

        return (HsaWsResponderInterface) proxyFactoryBean.create();
    }

    @Bean
    public HSAWebServiceCalls hsaWebServiceCalls() {
        HSAWebServiceCalls hsaWebServiceCalls = new HSAWebServiceCalls();
        hsaWebServiceCalls.setHsaLogicalAddress(hsaLogicalAddress);
        return hsaWebServiceCalls;
    }

}
