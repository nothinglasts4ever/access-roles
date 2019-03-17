package com.github.nl4.accessroles.rest;

import com.github.nl4.accessroles.domain.AccessRole;
import com.github.nl4.accessroles.service.AccessRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/access-roles")
public class AccessRoleController {

    private final AccessRoleService accessRolesService;

    @Autowired
    public AccessRoleController(AccessRoleService accessRolesService) {
        this.accessRolesService = accessRolesService;
    }

    @GetMapping
    public ResponseEntity<Iterable<AccessRole>> allAccessRoles() {
        Iterable<AccessRole> accessRoles = accessRolesService.allRoles();
        return ResponseEntity.ok(accessRoles);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccessRole> getAccessRole(@PathVariable Long id) {
        AccessRole accessRoles = accessRolesService.getAccessRole(id);
        return ResponseEntity.ok(accessRoles);
    }

    @PostMapping
    public ResponseEntity<Void> createAccessRole(@RequestBody AccessRole accessRoles, HttpServletRequest request) {
        AccessRole createdAccessRole = accessRolesService.createAccessRole(accessRoles);
        URI uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/access-roles/{id}")
                .buildAndExpand(createdAccessRole.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/id")
    public ResponseEntity<Void> updateAccessRole(@PathVariable Long id, @RequestBody AccessRole accessRoles) {
        accessRolesService.updateAccessRole(accessRoles, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/id")
    public ResponseEntity<Void> deleteAccessRole(@PathVariable Long id) {
        accessRolesService.deleteAccessRole(id);
        return ResponseEntity.noContent().build();
    }

}