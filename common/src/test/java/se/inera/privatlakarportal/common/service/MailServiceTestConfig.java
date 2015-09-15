package se.inera.privatlakarportal.common.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import se.inera.privatlakarportal.common.service.stub.JavaMailSenderAroundAdvice;
import se.inera.privatlakarportal.common.service.stub.MailStore;

@Configuration
@Profile("dev")
@PropertySource({"classpath:test.properties"})
@EnableAspectJAutoProxy
public class MailServiceTestConfig {

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.defaultEncoding}")
    private String defaultEncoding;

    @Value("${mail.port}")
    private String port;

    @Value("${mail.smtps.auth}")
    private String smtpsAuth;

    @Value("${mail.smtps.starttls.enable}")
    private String smtpsStarttlsEnable;

    @Value("${mail.smtps.debug}")
    private String smtpsDebug;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean 
    public MailService mailService() {
        return new MailServiceImpl();
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setDefaultEncoding(defaultEncoding);
        mailSender.setProtocol(protocol);

        if (!port.isEmpty()) {
            mailSender.setPort(Integer.parseInt(port));
        }
        if (!username.isEmpty()) {
            mailSender.setUsername(username);
        }
        if (!password.isEmpty()) {
            mailSender.setPassword(password);
        }

        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtps.auth", smtpsAuth);
        javaMailProperties.setProperty("mail.smtps.starttls.enable", smtpsStarttlsEnable);
        javaMailProperties.setProperty("mail.smtps.debug", smtpsDebug);
        if (protocol.equals("smtps")) {
            javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "true");
        }
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
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
