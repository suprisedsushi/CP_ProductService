package com.example.productservice.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//Spring will not create Bean of RestTemplate without @Configuration (Special class)
//@Configuration means This class contains Bean methods
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplateBuilder().build();
    }
}
