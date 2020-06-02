package com.github.nl4.owl.roles.controller;

import com.github.nl4.owl.roles.api.AccessRoleDto;
import com.github.nl4.owl.roles.service.AccessRoleService;
import com.github.nl4.owl.roles.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/access-roles")
@RequiredArgsConstructor
@Slf4j
public class AccessRoleController {

    private final AccessRoleService accessRolesService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<Collection<AccessRoleDto>> allAccessRoles() {
        var accessRoles = accessRolesService.allRoles();
        return ResponseEntity.ok(accessRoles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessRoleDto> getAccessRole(@PathVariable UUID id) {
        var accessRoles = accessRolesService.getAccessRole(id);
        return ResponseEntity.ok(accessRoles);
    }

    @PostMapping
    public ResponseEntity<Void> createAccessRole(@RequestBody @Valid AccessRoleDto accessRole, HttpServletRequest request) {
        var createdAccessRole = accessRolesService.createAccessRole(accessRole);
        var uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/access-roles/{id}")
                .buildAndExpand(createdAccessRole.getId())
                .toUri();
        log.info("Access role created: {}", uri);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAccessRole(@PathVariable UUID id, @RequestBody @Valid AccessRoleDto accessRole) {
        AccessRoleDto updatedRole = accessRolesService.updateAccessRole(accessRole, id);
        messageService.sendMessage(id, updatedRole);
        log.info("Access role with id [{}] updated", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessRole(@PathVariable UUID id) {
        accessRolesService.deleteAccessRole(id);
        log.info("Access role with id [{}] removed", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccessRolesForPerson(@RequestParam String personId) {
        accessRolesService.deleteAccessRolesForPerson(personId);
        log.info("All access roles for person with id [{}] to be removed", personId);
        return ResponseEntity.noContent().build();
    }

}