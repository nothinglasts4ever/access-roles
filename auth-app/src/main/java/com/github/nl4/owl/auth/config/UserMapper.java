package com.github.nl4.owl.auth.config;

import com.github.nl4.owl.auth.api.AppUserDto;
import com.github.nl4.owl.auth.api.CreateUserRequest;
import com.github.nl4.owl.auth.domain.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Set<AppUserDto> toAppUserDtoSet(Set<AppUser> users);

    @Mapping(source = "login", target = "credentials.login")
    @Mapping(source = "password", target = "credentials.password")
    AppUser toAppUser(CreateUserRequest request);

}