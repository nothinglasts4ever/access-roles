package com.github.nl4.owl.roles.repo;

import com.github.nl4.owl.roles.domain.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface LocationRepository extends CrudRepository<Location, UUID> {

    Set<Location> findAll();

}