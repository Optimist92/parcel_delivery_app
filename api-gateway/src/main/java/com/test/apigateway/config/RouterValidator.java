package com.test.apigateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints= List.of(
            "/auth/register",
            "/auth/login"
    );

    public static final List<String> adminEndpoints= List.of(
            "/delivery/orders/alert",
            "/auth/courier/register",
            "/delivery/admin/couriers",
            "/delivery/admin/orders",
            "/delivery/admin/orders/change_order_status"
    );

    public static final List<String> courierEndpoints= List.of(
            "/delivery/orders/courier/change_order_status"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isAdminSecured =
            request -> adminEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isCourierSecured =
            request -> courierEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

}