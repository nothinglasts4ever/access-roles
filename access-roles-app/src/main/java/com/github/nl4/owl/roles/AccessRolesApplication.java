package com.github.nl4.owl.roles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.github.nl4.owl.roles", "com.github.nl4.owl.common"})
@EnableDiscoveryClient
public class AccessRolesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessRolesApplication.class, args);
    }

}