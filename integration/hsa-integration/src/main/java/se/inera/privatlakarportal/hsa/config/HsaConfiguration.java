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
package se.inera.privatlakarportal.hsa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({"se.inera.privatlakarportal.hsa.services", "se.inera.privatlakarportal.hsa.monitoring", "se.inera.privatlakarportal.common.config"})
@Import(HsaStubConfiguration.class)
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
