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

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import se.inera.ifv.hsaws.v3.HsaWsResponderInterface;

@Configuration
@ComponentScan({
    "se.inera.ifv.privatlakarportal.spi.authorization.impl",
    "se.inera.intyg.privatlakarportal.hsa.services",
    "se.inera.intyg.privatlakarportal.hsa.monitoring",
    "se.inera.intyg.privatlakarportal.common.config"
})
@Import(HsaStubConfiguration.class)
@ImportResource("classpath:hsa-services-config.xml")
public class HsaConfiguration {

    @Value("${hsa.ws.services.url}")
    private String hsaWsUrl;

    @Bean
    public HsaWsResponderInterface hsaWebServiceClient() {
        JaxWsProxyFactoryBean proxyFactoryBean = new JaxWsProxyFactoryBean();
        proxyFactoryBean.setAddress(hsaWsUrl);
        proxyFactoryBean.setServiceClass(HsaWsResponderInterface.class);
        return (HsaWsResponderInterface) proxyFactoryBean.create();
    }

}
