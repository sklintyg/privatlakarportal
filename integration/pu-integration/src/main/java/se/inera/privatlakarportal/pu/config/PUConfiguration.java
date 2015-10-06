package se.inera.privatlakarportal.pu.config;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.jaxws.JaxWsClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.ConnectionType;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import se.inera.population.residentmaster.v1.LookupResidentForFullProfileResponderInterface;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Created by pebe on 2015-08-26.
 */
@Configuration
@ComponentScan("se.inera.privatlakarportal.pu.services")
@EnableCaching
@Import(PUStubConfiguration.class)
public class PUConfiguration {

    @Value("${putjanst.endpoint.url}")
    String puWsUrl;

    @Value("${ntjp.ws.key.manager.password}")
    String ntjpWsKeyManagerPassword;

    @Value("${ntjp.ws.certificate.file}")
    String ntjpWsCertificateFile;
    @Value("${ntjp.ws.certificate.password}")
    String ntjpWsCertificatePassword;
    @Value("${ntjp.ws.certificate.type}")
    String ntjpWsCertificateType;

    @Value("${ntjp.ws.truststore.file}")
    String ntjpWsTruststoreFile;
    @Value("${ntjp.ws.truststore.password}")
    String ntjpWsTruststorePassword;
    @Value("${ntjp.ws.truststore.type}")
    String ntjpWsTruststoreType;

    @Bean
    public LookupResidentForFullProfileResponderInterface puWebServiceClient() {
        JaxWsProxyFactoryBean proxyFactoryBean = new JaxWsProxyFactoryBean();
        proxyFactoryBean.setAddress(puWsUrl);
        proxyFactoryBean.setServiceClass(LookupResidentForFullProfileResponderInterface.class);
        return (LookupResidentForFullProfileResponderInterface) proxyFactoryBean.create();
    }

    @Bean
    public EhCacheCacheManager cacheManager() {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(ehCacheManagerFactory().getObject());
        return ehCacheCacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactory() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }

    @Bean
    @Profile("!dev")
    public HTTPConduit httpConduit() throws IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException {
        HTTPConduit httpConduit = (HTTPConduit) JaxWsClientProxy.getClient(puWebServiceClient()).getConduit();

        HTTPClientPolicy client = new HTTPClientPolicy();
        client.setAllowChunking(false);
        client.setAutoRedirect(true);
        client.setConnection(ConnectionType.KEEP_ALIVE);
        httpConduit.setClient(client);

        TLSClientParameters tlsClientParameters = new TLSClientParameters();
        tlsClientParameters.setDisableCNCheck(true);
        KeyStore trustStore = KeyStore.getInstance(ntjpWsCertificateType);

        try (FileInputStream trustStoreStream = new FileInputStream(ntjpWsCertificateFile)) {
            trustStore.load(trustStoreStream, ntjpWsCertificatePassword.toCharArray());
        }

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(trustStore, ntjpWsKeyManagerPassword.toCharArray());
        tlsClientParameters.setKeyManagers(kmf.getKeyManagers());

        KeyStore keyStore = KeyStore.getInstance(ntjpWsTruststoreType);

        try (FileInputStream keyStoreStream = new FileInputStream(ntjpWsTruststoreFile)) {
            keyStore.load(keyStoreStream, ntjpWsTruststorePassword.toCharArray());
        }

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
    }

}
