package com.github.nl4.owl.gateway.service;

import com.github.nl4.owl.gateway.repo.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, BadCredentialsException {
        var appUser = userRepository.findByCredentials_Login(login);
        if (appUser == null) {
            throw new UsernameNotFoundException(login);
        }
        var credentials = appUser.getCredentials();
        if (credentials == null) {
            throw new BadCredentialsException(login);
        }
        return new User(credentials.getLogin(), credentials.getPassword(), Collections.emptyList());
    }

}