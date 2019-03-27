package com.github.nl4.accessroles.persons.controller;

import com.github.nl4.accessroles.persons.client.AccessRolesClient;
import com.github.nl4.accessroles.persons.domain.Person;
import com.github.nl4.accessroles.persons.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
@Slf4j
public class PersonController {

    private final PersonService personService;
    private final AccessRolesClient accessRolesClient;

    @Autowired
    public PersonController(PersonService personService, AccessRolesClient accessRolesClient) {
        this.personService = personService;
        this.accessRolesClient = accessRolesClient;
    }

    @GetMapping
    public Flux<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<Person>> getPerson(@PathVariable String id) {
        return personService.getPerson(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Person>> createPerson(@RequestBody Person person) {
        return personService.createPerson(person)
                .map(p -> {
                    log.info("Person with id [" + p.getId() + "] created");
                    return new ResponseEntity<>(p, HttpStatus.CREATED);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<Person>> updatePerson(@PathVariable String id, @RequestBody Person person) {
        return personService.getPerson(id)
                .map(p -> {
                    personService.updatePerson(person, id);
                    log.info("Person with id [" + id + "] updated");
                    return ResponseEntity.ok(person);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable String id) {
        Mono<Void> deletePerson = personService.deletePerson(id);
        Mono<ResponseEntity<Void>> deleteRoles = Mono.just(accessRolesClient.deleteAccessRolesForPerson(id));
        return personService.getPerson(id)
                .flatMap(person -> Mono.zip(deletePerson, deleteRoles)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                        .doOnNext(x -> log.info("Person with id [" + id + "] removed"))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}