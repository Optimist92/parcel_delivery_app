package com.test.msorders;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MSOrdersApplication {


    public static void main(String[] args) {
        SpringApplication.run(MSOrdersApplication.class, args);

    }
}
