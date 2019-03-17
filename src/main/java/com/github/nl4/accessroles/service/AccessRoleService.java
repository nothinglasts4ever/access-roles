package com.github.nl4.accessroles.service;

import com.github.nl4.accessroles.domain.AccessRole;
import com.github.nl4.accessroles.repo.AccessRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
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
        AccessRole accessRole = getAccessRole(id);
        accessRoleRepository.delete(accessRole);
    }

}