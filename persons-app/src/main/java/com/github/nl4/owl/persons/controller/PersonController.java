package com.github.nl4.owl.persons.controller;

import com.github.nl4.owl.persons.api.PersonDto;
import com.github.nl4.owl.persons.client.AccessRolesClient;
import com.github.nl4.owl.persons.service.MessageService;
import com.github.nl4.owl.persons.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;
    private final AccessRolesClient accessRolesClient;
    private final MessageService messageService;

    @GetMapping
    public Flux<PersonDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PersonDto>> getPerson(@PathVariable UUID id) {
        return personService.getPerson(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<PersonDto>> createPerson(@RequestBody PersonDto person) {
        return personService.createPerson(person)
                .map(p -> {
                    log.info("Person with id [{}] created", p.getId());
                    return new ResponseEntity<>(p, HttpStatus.CREATED);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<PersonDto>> updatePerson(@PathVariable UUID id, @RequestBody PersonDto person) {
        return personService.updatePerson(person, id)
                .map(p -> {
                    messageService.sendPersonUpdated(p);
                    log.info("Person with id [{}] updated", id);
                    return ResponseEntity.ok(person);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable UUID id) {
        return personService.getPerson(id)
                .flatMap(person -> personService.deletePerson(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                        .doOnNext(x -> {
                            log.info("Person with id [{}] removed", id);
                            accessRolesClient.deleteAccessRolesForPerson(id);
                            messageService.sendPersonDeleted(id);
                        })
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}