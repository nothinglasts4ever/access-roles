package com.github.nl4.owl.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAPIApplication.class, args);
    }

}