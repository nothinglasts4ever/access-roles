package com.github.nl4.accessroles.persons.repo;

import com.github.nl4.accessroles.persons.domain.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
}