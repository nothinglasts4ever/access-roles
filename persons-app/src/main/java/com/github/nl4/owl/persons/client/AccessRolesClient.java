package com.github.nl4.owl.persons.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("access-roles-app")
public interface AccessRolesClient {

    @DeleteMapping("/access-roles")
    ResponseEntity<Void> deleteAccessRolesForPerson(@RequestParam("personId") UUID personId);

}