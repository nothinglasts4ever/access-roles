package com.github.nl4.owl.roles.config;

import com.github.nl4.owl.common.rest.ControllerExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    ControllerExceptionHandler controllerExceptionHandler() {
        return new ControllerExceptionHandler();
    }

}