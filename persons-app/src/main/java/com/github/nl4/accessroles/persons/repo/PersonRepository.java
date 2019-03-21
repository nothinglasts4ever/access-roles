package com.github.nl4.accessroles.persons.repo;

import com.github.nl4.accessroles.persons.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}