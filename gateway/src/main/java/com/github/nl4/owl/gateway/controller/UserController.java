package com.github.nl4.owl.gateway.controller;

import com.github.nl4.owl.gateway.domain.AppUser;
import com.github.nl4.owl.gateway.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public Iterable<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public void signUp(@RequestBody AppUser appUser) {
        var credentials = appUser.getCredentials();
        if (credentials == null) {
            throw new ValidationException("Credentials were not provided");
        }
        credentials.setPassword(bCryptPasswordEncoder.encode(credentials.getPassword()));
        appUser.setCredentials(credentials);
        userRepository.save(appUser);
    }

}