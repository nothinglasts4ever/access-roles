package com.github.nl4.owl.roles.repo;

import com.github.nl4.owl.roles.domain.AccessRole;
import org.springframework.data.repository.CrudRepository;

public interface AccessRoleRepository extends CrudRepository<AccessRole, Long> {
    void deleteAccessRolesByLocation_Id(Long locationId);
    Long countByPersonId(String personId);
    void deleteAccessRolesByPersonId(String personId);
}