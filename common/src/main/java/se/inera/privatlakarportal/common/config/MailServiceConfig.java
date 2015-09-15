package se.inera.privatlakarportal.common.config;

import java.util.Properties;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import se.inera.privatlakarportal.common.service.MailService;
import se.inera.privatlakarportal.common.service.MailServiceImpl;

@Configuration
@PropertySource({"file:${privatlakarportal.config.file}", "file:${privatlakarportal.mailresource.file}", "classpath:default.properties"})
@EnableAsync
public class MailServiceConfig implements AsyncConfigurer{

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

    @Value("${mail.smtps.auth}")
    private String smtpsAuth;

    @Value("${mail.smtps.starttls.enable}")
    private String smtpsStarttlsEnable;

    @Value("${mail.smtps.debug}")
    private String smtpsDebug;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setDefaultEncoding(defaultEncoding);
        mailSender.setProtocol(protocol);
        if (username != null) {
            mailSender.setUsername(username);
        }
        if (password != null) {
            mailSender.setPassword(password);
        }

        Properties javaMailProperties = new Properties();
        if (smtpsAuth != null) {
            javaMailProperties.setProperty("mail.smtps.auth", smtpsAuth);
        }
        if (smtpsStarttlsEnable != null) {
            javaMailProperties.setProperty("mail.smtps.starttls.enable", smtpsStarttlsEnable);
        }
        if (smtpsDebug != null) {
            javaMailProperties.setProperty("mail.smtps.debug", smtpsDebug);
        }
        if (protocol.equals("smtps")) {
            javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "true");
        }
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    public MailService mailService() {
        return new MailServiceImpl(); 
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(42);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
}
