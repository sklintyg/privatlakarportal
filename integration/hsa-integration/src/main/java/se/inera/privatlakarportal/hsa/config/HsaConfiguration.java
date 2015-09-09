package se.inera.privatlakarportal.hsa.config;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.jaxws.JaxWsClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.ConnectionType;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import se.inera.ifv.hsaws.v3.HsaWsResponderInterface;
import se.inera.ifv.privatlakarportal.spi.authorization.impl.HSAWebServiceCalls;
import se.inera.privatlakarportal.common.config.MailServiceStubConfig;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Configuration
@ComponentScan({"se.inera.privatlakarportal.hsa.services", "se.inera.privatlakarportal.common.service"})
@Import({HsaStubConfiguration.class, MailServiceStubConfig.class})
@ImportResource("classpath:hsa-services-config.xml")
public class HsaConfiguration {

/*    @Value("${hsa.ws.service.logicaladdress}")
    private String hsaLogicalAddress;

    @Value("${hsa.ws.services.url}")
    private String hsaWsUrl;

    @Value("${hsa.ws.key.manager.password}")
    String hsaWsKeyManagerPassword;

    @Value("${hsa.ws.certificate.file}")
    String hsaWsCertificateFile;
    @Value("${hsa.ws.certificate.password}")
    String hsaWsCertificatePassword;
    @Value("${hsa.ws.certificate.type}")
    String hsaWsCertificateType;

    @Value("${hsa.ws.truststore.file}")
    String hsaWsTruststoreFile;
    @Value("${hsa.ws.truststore.password}")
    String hsaWsTruststorePassword;
    @Value("${hsa.ws.truststore.type}")
    String hsaWsTruststoreType;

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

    @Bean
    @Profile("!dev")
    public HTTPConduit httpConduit() throws IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException {
        HTTPConduit httpConduit = (HTTPConduit) JaxWsClientProxy.getClient(hsaWebServiceClient()).getConduit();

        HTTPClientPolicy client = new HTTPClientPolicy();
        client.setAllowChunking(false);
        client.setAutoRedirect(true);
        client.setConnection(ConnectionType.KEEP_ALIVE);
        httpConduit.setClient(client);

        TLSClientParameters tlsClientParameters = new TLSClientParameters();
        tlsClientParameters.setDisableCNCheck(true);

        KeyStore trustStore = KeyStore.getInstance(hsaWsCertificateType);
        trustStore.load(new FileInputStream(hsaWsCertificateFile), hsaWsCertificatePassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(trustStore, hsaWsKeyManagerPassword.toCharArray());
        tlsClientParameters.setKeyManagers(kmf.getKeyManagers());

        KeyStore keyStore = KeyStore.getInstance(hsaWsTruststoreType);
        keyStore.load(new FileInputStream(hsaWsTruststoreFile), hsaWsTruststorePassword.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(keyStore);
        tlsClientParameters.setTrustManagers(tmf.getTrustManagers());

        FiltersType filters = new FiltersType();
        filters.getInclude().add(".*_EXPORT_.*");
        filters.getInclude().add(".*_EXPORT1024_.*");
        filters.getInclude().add(".*_WITH_AES_256_.*");
        filters.getInclude().add(".*_WITH_AES_128_.*");
        filters.getInclude().add(".*_WITH_3DES_.*");
        filters.getInclude().add(".*_WITH_DES_.*");
        filters.getInclude().add(".*_WITH_NULL_.*");
        filters.getInclude().add(".*_DH_anon_.*");
        tlsClientParameters.setCipherSuitesFilter(filters);

        httpConduit.setTlsClientParameters(tlsClientParameters);

        return httpConduit;
    }*/

}
