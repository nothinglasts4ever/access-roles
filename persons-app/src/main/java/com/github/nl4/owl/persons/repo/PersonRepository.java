package com.github.nl4.owl.persons.repo;

import com.github.nl4.owl.persons.domain.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
}