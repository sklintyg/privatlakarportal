package se.inera.privatlakarportal.common.config;

import java.util.Properties;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@PropertySource({ "file:${privatlakarportal.config.file}", "file:${privatlakarportal.mailresource.file}", "classpath:default.properties" })
@EnableAsync
public class MailServiceConfig implements AsyncConfigurer {

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
    private boolean smtpsAuth;

    @Value("${mail.smtps.starttls.enable}")
    private boolean smtpsStarttlsEnable;

    @Value("${mail.smtps.debug}")
    private boolean smtpsDebug;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        int intPort = Integer.parseInt(port);
        mailSender.setHost(mailHost);
        mailSender.setDefaultEncoding(defaultEncoding);
        mailSender.setProtocol(protocol);
        mailSender.setPort(intPort);
        mailSender.setUsername(username.isEmpty() ? "default" : username);
        mailSender.setPassword(password.isEmpty() ? "default" : password);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail."+protocol+".auth", smtpsAuth);
        javaMailProperties.put("mail."+protocol+".port", intPort);
        javaMailProperties.put("mail."+protocol+".starttls.enable", smtpsStarttlsEnable);
        javaMailProperties.put("mail."+protocol+".debug", smtpsDebug);
        javaMailProperties.put("mail."+protocol+".socketFactory.fallback", true);

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
