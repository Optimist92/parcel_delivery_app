package com.test.msorders.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import util.JwtUtil;

@Configuration
@RequiredArgsConstructor
public class OrderConfig {

    private final RestTemplateBuilder restTemplateBuilder;
    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

    @Bean
    public JwtUtil jwtUtilBean() {
        return new JwtUtil();
    }

}