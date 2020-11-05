package io.avalia.fruits.configuration;

import com.fasterxml.jackson.core.type.ResolvedType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.HttpAuthenticationBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.singletonList;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Fruits API")
            .description("An API to demonstrate Swagger and Spring Boot")
            .version("0.2.0")
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.OAS_30)
            .select()
                .apis(RequestHandlerSelectors.basePackage("io.avalia.fruits.api"))
                .build()
            .directModelSubstitute(Void.class, Void.class)
            //.directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
            //.directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
            .securitySchemes(singletonList(securityScheme()))
            .securityContexts(singletonList(securityContext()))
            .apiInfo(apiInfo());
    }

    private SecurityScheme securityScheme() {
        return new HttpAuthenticationBuilder()
          .name("BearerAuthorization")
          .scheme("bearer")
          .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
          .operationSelector(new Predicate<OperationContext>() {
              @Override
              public boolean test(OperationContext operationContext) {
                  return true;
              }
          })
          .securityReferences(securityReferences())
          .build();
    }

    private List<SecurityReference> securityReferences() {
        return singletonList(
          new SecurityReference("BearerAuthorization", new AuthorizationScope[] {}));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
