package com.github.nl4.owl.roles.service;

import com.github.nl4.owl.roles.api.AccessRoleDto;
import com.github.nl4.owl.roles.config.AccessRoleMapper;
import com.github.nl4.owl.roles.domain.AccessRole;
import com.github.nl4.owl.roles.repo.AccessRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    public AccessRoleDto createAccessRole(AccessRoleDto dto) {
        var entity = mapper.toAccessRole(dto);
        entity.setId(UUID.randomUUID());

        return mapper.toAccessRoleDto(accessRoleRepository.save(entity));
    }

    @Transactional
    public AccessRoleDto updateAccessRole(AccessRoleDto accessRole, UUID id) {
        var dbAccessRole = doGetAccessRole(id);
        dbAccessRole.setLocation(mapper.toLocation(accessRole.getLocation()));
        dbAccessRole.setStartTime(accessRole.getStartTime());
        dbAccessRole.setEndTime(accessRole.getEndTime());

        return mapper.toAccessRoleDto(dbAccessRole);
    }

    @Transactional
    public void deleteAccessRole(UUID id) {
        doGetAccessRole(id);
        accessRoleRepository.deleteById(id);
    }

    @Transactional
    public void deleteAccessRolesForPerson(String personId) {
        accessRoleRepository.deleteAccessRolesByPersonId(personId);
    }

    private AccessRole doGetAccessRole(UUID id) {
        return accessRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find access role with id [" + id + "]"));
    }

}