package se.inera.privatlakarportal.common.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import se.inera.privatlakarportal.common.service.MailService;
import se.inera.privatlakarportal.common.service.MailServiceImpl;
import se.inera.privatlakarportal.common.service.stub.JavaMailSenderAroundAdvice;
import se.inera.privatlakarportal.common.service.stub.MailStore;

@Configuration
@Profile("dev")
@EnableAspectJAutoProxy
public class MailServiceTestConfig {

    @Bean 
    public MailService mailService() {
        return new MailServiceImpl();
    }

    @Bean
    public JavaMailSender mailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean
    JavaMailSenderAroundAdvice javaMailSenderAroundAdvice() {
        return new JavaMailSenderAroundAdvice();
    }

    @Bean
    MailStore mailstore() {
        return new MailStore();
        
    }
}
