package com.github.nl4.owl.roles.repo;

import com.github.nl4.owl.roles.domain.AccessRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface AccessRoleRepository extends CrudRepository<AccessRole, UUID> {

    @EntityGraph(attributePaths = {"location"})
    Set<AccessRole> findAll();

    Set<AccessRole> findAllByLocationId(UUID LocationId);

    Set<AccessRole> findAllByPersonId(UUID personId);

}