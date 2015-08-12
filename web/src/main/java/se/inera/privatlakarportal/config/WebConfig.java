package se.inera.privatlakarportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan("se.inera.privatlakarportal.web, se.inera.privatlakarportal.service")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(InternalResourceView.class);
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".html");
        return viewResolver;

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        int cachePeriod = 3600 * 24 * 15;
        registry.addResourceHandler("/index.html").addResourceLocations("/").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/robots.txt").addResourceLocations("/").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/bower_components/**").addResourceLocations("/bower_components/");
        registry.addResourceHandler("/app/**").addResourceLocations("/app/");
        registry.addResourceHandler("/components/**").addResourceLocations("/components/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
