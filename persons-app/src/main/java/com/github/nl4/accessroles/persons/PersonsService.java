package com.github.nl4.accessroles.persons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.github.nl4.accessroles.persons", "com.github.nl4.accessroles.common"})
@EnableDiscoveryClient
@EnableFeignClients
public class PersonsService {

    public static void main(String[] args) {
        SpringApplication.run(PersonsService.class, args);
    }

}