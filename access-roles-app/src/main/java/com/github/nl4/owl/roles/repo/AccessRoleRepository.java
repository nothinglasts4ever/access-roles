package com.github.nl4.owl.roles.repo;

import com.github.nl4.owl.roles.domain.AccessRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface AccessRoleRepository extends CrudRepository<AccessRole, UUID> {

    @EntityGraph(attributePaths = {"location"})
    Set<AccessRole> findAll();

    @Query("DELETE FROM AccessRole role WHERE role.location.id = :locationId")
    @Modifying
    void deleteAccessRolesByLocation_Id(UUID locationId);

    @Query("DELETE FROM AccessRole role WHERE role.personId = :personId")
    @Modifying
    void deleteAccessRolesByPersonId(String personId);

}