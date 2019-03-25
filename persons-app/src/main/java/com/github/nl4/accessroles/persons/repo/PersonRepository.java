package com.github.nl4.accessroles.persons.repo;

import com.github.nl4.accessroles.persons.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
}