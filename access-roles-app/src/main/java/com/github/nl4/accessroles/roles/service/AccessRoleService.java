package com.github.nl4.accessroles.roles.service;

import com.github.nl4.accessroles.roles.domain.AccessRole;
import com.github.nl4.accessroles.roles.repo.AccessRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class AccessRoleService {

    private final AccessRoleRepository accessRoleRepository;

    @Autowired
    public AccessRoleService(AccessRoleRepository accessRoleRepository) {
        this.accessRoleRepository = accessRoleRepository;
    }

    public Iterable<AccessRole> allRoles() {
        return accessRoleRepository.findAll();
    }

    public AccessRole getAccessRole(Long id) {
        return accessRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find access role with id [" + id + "]"));
    }

    public AccessRole createAccessRole(AccessRole accessRole) {
        return accessRoleRepository.save(accessRole);
    }

    public AccessRole updateAccessRole(AccessRole accessRole, Long id) {
        getAccessRole(id);
        accessRole.setId(id);
        return accessRoleRepository.save(accessRole);
    }

    public void deleteAccessRole(Long id) {
        var accessRole = getAccessRole(id);
        accessRoleRepository.delete(accessRole);
    }

    public void deleteAccessRolesForPerson(String personId) {
        if (accessRoleRepository.countByPersonId(personId) > 0) {
            accessRoleRepository.deleteAccessRolesByPersonId(personId);
            log.info("Access roles for person with id [" + personId + "] removed");
        }
    }

}