package com.test.apigateway.config;

import com.test.apigateway.filter.JwtAuthenticationFilter;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("ms-orders", r -> r.path("/").filters(f -> f.filter(filter)).uri("lb://ms-orders"))
                .route("ms-couriers", r -> r.path("/").filters(f -> f.filter(filter)).uri("lb://ms-couriers"))
                .route("ms-batches", r -> r.path("/").filters(f -> f.filter(filter)).uri("lb://ms-batches")).build();
    }
}
