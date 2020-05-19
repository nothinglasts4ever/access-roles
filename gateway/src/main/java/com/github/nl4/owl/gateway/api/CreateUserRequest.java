package com.github.nl4.owl.gateway.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {

    private String firstName;
    private String lastName;
    @NotBlank(message = "Login was not provided")
    private String login;
    @NotBlank(message = "Password was not provided")
    private String password;

}