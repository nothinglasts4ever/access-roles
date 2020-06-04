package com.github.nl4.owl.roles.service;

import com.github.nl4.owl.roles.api.AccessRoleDto;
import com.github.nl4.owl.roles.api.AccessRoleRequest;
import com.github.nl4.owl.roles.config.AccessRoleMapper;
import com.github.nl4.owl.roles.domain.AccessRole;
import com.github.nl4.owl.roles.repo.AccessRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccessRoleService {

    private final AccessRoleRepository accessRoleRepository;
    private final AccessRoleMapper mapper;

    @Transactional(readOnly = true)
    public Collection<AccessRoleDto> allRoles() {
        return mapper.toAccessRoleDtoSet(accessRoleRepository.findAll());
    }

    @Transactional(readOnly = true)
    public AccessRoleDto getAccessRole(UUID id) {
        return mapper.toAccessRoleDto(doGetAccessRole(id));
    }

    @Transactional
    public AccessRoleDto createAccessRole(AccessRoleRequest request) {
        var entity = mapper.toAccessRole(request);
        entity.setId(UUID.randomUUID());

        return mapper.toAccessRoleDto(accessRoleRepository.save(entity));
    }

    @Transactional
    public AccessRoleDto updateAccessRole(AccessRoleRequest request, UUID id) {
        var accessRole = doGetAccessRole(id);
        accessRole.setStartTime(request.getStartTime());
        accessRole.setEndTime(request.getEndTime());

        return mapper.toAccessRoleDto(accessRole);
    }

    @Transactional
    public void deleteAccessRole(UUID id) {
        var accessRole = doGetAccessRole(id);
        accessRole.setDeletedAt(OffsetDateTime.now());
    }

    @Transactional
    public void deleteAccessRolesForPerson(UUID personId) {
        accessRoleRepository.findAllByPersonId(personId)
                .forEach(role -> role.setDeletedAt(OffsetDateTime.now()));
    }

    @Transactional
    public void deleteAccessRolesForLocation(UUID locationId) {
        accessRoleRepository.findAllByLocationId(locationId)
                .forEach(role -> role.setDeletedAt(OffsetDateTime.now()));
    }

    private AccessRole doGetAccessRole(UUID id) {
        return accessRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find access role with id [" + id + "]"));
    }

}