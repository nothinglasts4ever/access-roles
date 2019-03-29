package com.github.nl4.owl.persons.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "access-roles-app")
public interface AccessRolesClient {
    @RequestMapping(method = RequestMethod.DELETE, value = "/access-roles")
    ResponseEntity<Void> deleteAccessRolesForPerson(@RequestParam String personId);
}