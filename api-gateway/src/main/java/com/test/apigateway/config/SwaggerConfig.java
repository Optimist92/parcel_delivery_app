package com.test.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Primary
@RequiredArgsConstructor
public class SwaggerConfig implements SwaggerResourcesProvider {

    public static final String API_URI = "/v3/api-docs";
    private final RouteDefinitionLocator routeLocator;

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("1.0");
        return swaggerResource;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        routeLocator.getRouteDefinitions().subscribe(
                routeDefinition -> {
                    String resourceName = routeDefinition.getId().substring(33);
                    String location = routeDefinition.getPredicates().get(0).getArgs().get("pattern").replace("/**", API_URI);
                    resources.add(
                            swaggerResource(resourceName, location)
                    );
                }
        );
        return resources;
    }
}
