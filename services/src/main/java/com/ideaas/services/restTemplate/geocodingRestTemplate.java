    package com.ideaas.services.restTemplate;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.boot.web.client.RestTemplateBuilder;
    import org.springframework.web.client.RestTemplate;

    @Configuration
    public class geocodingRestTemplate {

        @Bean
        RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder
            .basicAuthentication("SCOPESI", "SCOPESI-wXtKUyw!DpiJ2VIf/-I7b04lFb8F67xqdw/8qRICrCz4yyxKks4I5g62Dzc")
            .build();  
        }
        
    }
