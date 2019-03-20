package com.github.nl4.accessroles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PersonsService {

    public static void main(String[] args) {
        SpringApplication.run(PersonsService.class, args);
    }

}