package com.poc.movieticketbookingplatform.config;

import org.ff4j.FF4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4jConfig {

    @Bean
    public FF4j getFF4j() {
        FF4j ff4j = new FF4j();
        ff4j.createFeature("newFeature");
        return ff4j;
    }
}