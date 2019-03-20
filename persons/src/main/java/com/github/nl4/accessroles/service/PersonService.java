package com.github.nl4.accessroles.service;

import com.github.nl4.accessroles.domain.Person;
import com.github.nl4.accessroles.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Iterable<Person> allPersons() {
        return personRepository.findAll();
    }

    public Person getPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find person with id [" + id + "]"));
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Person person, Long id) {
        getPerson(id);
        person.setId(id);
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        var person = getPerson(id);
        personRepository.delete(person);
    }

}