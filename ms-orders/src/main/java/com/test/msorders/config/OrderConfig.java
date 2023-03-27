package com.test.msorders.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import util.JwtUtil;

@Configuration
public class OrderConfig {

    @Bean
    public JwtUtil jwtUtilBean() {
        return new JwtUtil();
    }
}
