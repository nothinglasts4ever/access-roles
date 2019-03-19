package com.github.nl4.accessroles.repo;

import com.github.nl4.accessroles.domain.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}