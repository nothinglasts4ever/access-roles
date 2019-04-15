package com.github.nl4.owl.gateway.repo;

import com.github.nl4.owl.gateway.domain.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByCredentials_Login(String login);
}