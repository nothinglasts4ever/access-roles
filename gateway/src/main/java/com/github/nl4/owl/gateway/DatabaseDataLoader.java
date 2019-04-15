package com.github.nl4.owl.gateway;

import com.github.nl4.owl.gateway.domain.AppUser;
import com.github.nl4.owl.gateway.domain.Credentials;
import com.github.nl4.owl.gateway.repo.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DatabaseDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public DatabaseDataLoader(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        var credentials1 = Credentials.builder()
                .login("Duff123")
                .password(bCryptPasswordEncoder.encode("secret1"))
                .build();
        var credentials2 = Credentials.builder()
                .login("ElBarto789")
                .password(bCryptPasswordEncoder.encode("secret3"))
                .build();
        var credentials3 = Credentials.builder()
                .login("852")
                .password(bCryptPasswordEncoder.encode("haha"))
                .build();
        var honer = AppUser.builder()
                .firstName("Homer")
                .lastName("Simpson")
                .credentials(credentials1)
                .build();
        var bart = AppUser.builder()
                .firstName("Bart")
                .lastName("Simpson")
                .credentials(credentials2)
                .build();
        var nelson = AppUser.builder()
                .firstName("Nelson")
                .lastName("Muntz")
                .credentials(credentials3)
                .build();
        userRepository.saveAll(Set.of(honer, bart, nelson));
    }

}