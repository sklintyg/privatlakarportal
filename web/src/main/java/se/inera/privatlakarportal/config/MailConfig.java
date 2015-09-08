package se.inera.privatlakarportal.config;

import java.util.Properties;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@PropertySource({"file:${privatlakarportal.config.file}", "classpath:default.properties"})
@EnableAsync
public class MailConfig implements AsyncConfigurer{

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
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtps.auth", smtpsAuth);
        javaMailProperties.setProperty("mail.smtps.starttls.enable", smtpsStarttlsEnable);
        javaMailProperties.setProperty("mail.smtps.debug", smtpsDebug);
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
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
        // TODO Auto-generated method stub
        return null;
    }

}
