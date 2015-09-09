package se.inera.privatlakarportal.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by pebe on 2015-09-07.
 */
@Configuration
@ComponentScan({"se.inera.privatlakarportal.service", "se.inera.privatlakarportal.common.service.stub"})
@EnableScheduling
public class ServiceConfig {
}
