package se.inera.privatlakarportal.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import se.inera.privatlakarportal.service.mail.stub.JavaMailSenderAroundAdvice;
import se.inera.privatlakarportal.service.mail.stub.MailStore;

@Configuration
@ComponentScan("se.inera.privatlakarportal.service.mail.stub")
@EnableAspectJAutoProxy
public class MailServiceImplTestConfig {

    @Bean 
    public MailService mailService() {
        return new MailServiceImpl();
    }

    // this bean will be injected into the OrderServiceTest class
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
