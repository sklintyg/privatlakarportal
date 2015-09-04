package se.inera.privatlakarportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@PropertySource({"file:${privatlakarportal.config.file}", "file:${credentials.file}"})
@ImportResource({ "classpath:META-INF/cxf/cxf.xml", "classpath:securityContext.xml"})
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@EnableScheduling
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
