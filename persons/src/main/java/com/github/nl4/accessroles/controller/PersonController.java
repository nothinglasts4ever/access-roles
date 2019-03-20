package com.github.nl4.accessroles.controller;

import com.github.nl4.accessroles.domain.Person;
import com.github.nl4.accessroles.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Person>> allPersons() {
        var persons = personService.allPersons();
        return ResponseEntity.ok(persons);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        var person = personService.getPerson(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody Person person, HttpServletRequest request) {
        var createdPerson = personService.createPerson(person);
        var uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/persons/{id}")
                .buildAndExpand(createdPerson.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/id")
    public ResponseEntity<Void> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        personService.updatePerson(person, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/id")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}