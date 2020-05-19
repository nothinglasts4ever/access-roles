package com.github.nl4.owl.gateway.api;

import lombok.Data;

import java.util.UUID;

@Data
public class AppUserDto {

    private UUID id;
    private String firstName;
    private String lastName;

}