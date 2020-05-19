package com.github.nl4.owl.persons.service;

import com.github.nl4.owl.persons.api.PersonDto;
import com.github.nl4.owl.persons.config.PersonMapper;
import com.github.nl4.owl.persons.domain.Person;
import com.github.nl4.owl.persons.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper mapper;

    public Flux<PersonDto> getAllPersons() {
        return personRepository.findAll()
                .map(mapper::toPersonDto);
    }

    public Mono<PersonDto> getPerson(UUID id) {
        return personRepository.findById(id)
                .map(mapper::toPersonDto);
    }

    public Mono<PersonDto> createPerson(PersonDto person) {
        var entity = mapper.toPerson(person);
        entity.setId(UUID.randomUUID());

        return personRepository.save(entity)
                .map(mapper::toPersonDto);
    }

    public Mono<PersonDto> updatePerson(PersonDto person, UUID id) {
        var updatedPerson = mapper.toPerson(person);
        updatedPerson.setId(id);

        return personRepository.save(updatedPerson)
                .map(mapper::toPersonDto);
    }

    public Mono<Void> deletePerson(UUID id) {
        return personRepository.deleteById(id);
    }

}