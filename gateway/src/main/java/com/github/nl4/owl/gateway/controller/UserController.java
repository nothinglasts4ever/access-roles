package com.github.nl4.owl.gateway.controller;

import com.github.nl4.owl.gateway.api.AppUserDto;
import com.github.nl4.owl.gateway.api.CreateUserRequest;
import com.github.nl4.owl.gateway.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<AppUserDto> getAllUsers() {
        return userService.allUsers();
    }

    @PostMapping("/register")
    public void signUp(@RequestBody @Valid CreateUserRequest request) {
        userService.createAccessRole(request);
        log.info("User created: {} {}", request.getFirstName(), request.getLastName());
    }

}