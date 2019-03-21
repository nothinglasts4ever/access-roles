package com.github.nl4.accessroles.controller;

import com.github.nl4.accessroles.domain.AccessRole;
import com.github.nl4.accessroles.service.AccessRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/access-roles")
@Slf4j
public class AccessRoleController {

    private final AccessRoleService accessRolesService;

    @Autowired
    public AccessRoleController(AccessRoleService accessRolesService) {
        this.accessRolesService = accessRolesService;
    }

    @GetMapping
    public ResponseEntity<Iterable<AccessRole>> allAccessRoles() {
        var accessRoles = accessRolesService.allRoles();
        return ResponseEntity.ok(accessRoles);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccessRole> getAccessRole(@PathVariable Long id) {
        var accessRoles = accessRolesService.getAccessRole(id);
        return ResponseEntity.ok(accessRoles);
    }

    @PostMapping
    public ResponseEntity<Void> createAccessRole(@RequestBody AccessRole accessRoles, HttpServletRequest request) {
        var createdAccessRole = accessRolesService.createAccessRole(accessRoles);
        var uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/access-roles/{id}")
                .buildAndExpand(createdAccessRole.getId())
                .toUri();
        log.info("Access role created: " + uri);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateAccessRole(@PathVariable Long id, @RequestBody AccessRole accessRoles) {
        accessRolesService.updateAccessRole(accessRoles, id);
        log.info("Access role with id [" + id + "] updated");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccessRole(@PathVariable Long id) {
        accessRolesService.deleteAccessRole(id);
        log.info("Access role with id [" + id + "] removed");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccessRolesForPerson(@RequestParam Long personId) {
        accessRolesService.deleteAccessRolesForPerson(personId);
        log.info("Access roles for person with id [" + personId + "] removed");
        return ResponseEntity.noContent().build();
    }

}