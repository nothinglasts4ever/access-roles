package com.github.nl4.owl.gateway.config;

import com.github.nl4.owl.gateway.api.AppUserDto;
import com.github.nl4.owl.gateway.api.CreateUserRequest;
import com.github.nl4.owl.gateway.domain.AppUser;
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