package com.github.nl4.owl.persons.service;

import com.github.nl4.owl.persons.domain.Person;
import com.github.nl4.owl.persons.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Mono<Person> getPerson(String id) {
        return personRepository.findById(id);
    }

    public Mono<Person> createPerson(Person person) {
        return personRepository.save(person);
    }

    public Mono<Person> updatePerson(Person person, String id) {
        person.setId(id);
        return personRepository.save(person);
    }

    public Mono<Void> deletePerson(String id) {
        return personRepository.deleteById(id);
    }

}