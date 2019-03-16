package com.github.nl4.accessroles.repo;

import com.github.nl4.accessroles.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}