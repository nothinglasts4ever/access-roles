package com.github.nl4.owl.gateway.service;

import com.github.nl4.owl.gateway.api.AppUserDto;
import com.github.nl4.owl.gateway.api.CreateUserRequest;
import com.github.nl4.owl.gateway.config.UserMapper;
import com.github.nl4.owl.gateway.domain.AppUser;
import com.github.nl4.owl.gateway.domain.Credentials;
import com.github.nl4.owl.gateway.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper mapper;

    @Transactional(readOnly = true)
    public Collection<AppUserDto> allUsers() {
        return mapper.toAppUserDtoSet(userRepository.findAll());
    }

    @Transactional
    public void createAccessRole(CreateUserRequest request) {
        var credentials = Credentials.builder()
                .login(request.getLogin())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();

        var user = AppUser.builder()
                .id(UUID.randomUUID())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .credentials(credentials)
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, BadCredentialsException {
        var appUser = userRepository.findByCredentials_Login(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login [" + login + "] not found"));

        var credentials = appUser.getCredentials();
        if (credentials == null) throw new BadCredentialsException("User with login [" + login + "] not found");

        return new User(credentials.getLogin(), credentials.getPassword(), Collections.emptyList());
    }

}