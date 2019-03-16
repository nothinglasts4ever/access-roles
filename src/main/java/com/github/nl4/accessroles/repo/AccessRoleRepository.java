package com.github.nl4.accessroles.repo;

import com.github.nl4.accessroles.domain.AccessRole;
import org.springframework.data.repository.CrudRepository;

public interface AccessRoleRepository extends CrudRepository<AccessRole, Long> {
}