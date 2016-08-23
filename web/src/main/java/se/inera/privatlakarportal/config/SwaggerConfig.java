package se.inera.privatlakarportal.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/api/.*"))
                .build()
            .produces(buildMediaTypes())
            .pathMapping("/")
            .useDefaultResponseMessages(false)
            .apiInfo(metadata());
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("REST-api:er för Privatläkarportalen")
                .description("Här listas alla REST-api:er som finns tillgängliga för applikationen Privatläkarportalen. ")
                .version("1.0")
                .build();
    }
    
    private Set<String> buildMediaTypes(){
        Set<String> mediaTypesSet = new HashSet<String>();
        mediaTypesSet.add("application/json");
        return mediaTypesSet;
    }
}
