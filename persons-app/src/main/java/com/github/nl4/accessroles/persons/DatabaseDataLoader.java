package com.github.nl4.accessroles.persons;

import com.github.nl4.accessroles.common.data.Gender;
import com.github.nl4.accessroles.persons.domain.Address;
import com.github.nl4.accessroles.persons.domain.Person;
import com.github.nl4.accessroles.persons.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class DatabaseDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final PersonRepository personRepository;

    @Autowired
    public DatabaseDataLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Address address1 = Address.builder().city("Black").street("41st Street").building(234).apartment(2342).build();
        Address address2 = Address.builder().city("Yellow").street("32nd Street").building(123).apartment(9876).build();
        Address address3 = Address.builder().city("Red").street("23rd Street").building(456).apartment(1).build();
        Person rick = Person.builder()
                .id("1")
                .firstName("Rick")
                .lastName("Sanchez")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(70))
                .addresses(Set.of(address1, address2))
                .build();
        Person morty = Person.builder()
                .id("2")
                .firstName("Morty")
                .lastName("Smith")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(14))
                .addresses(Set.of(address2))
                .build();
        Person summer = Person.builder()
                .id("3")
                .firstName("Summer")
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.now().minusYears(17))
                .addresses(Set.of(address2))
                .build();
        Person beth = Person.builder()
                .id("4")
                .firstName("Beth")
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.now().minusYears(34))
                .addresses(Set.of(address2))
                .build();
        Person jerry = Person.builder()
                .id("5")
                .firstName("Jerry")
                .lastName("Smith")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(34))
                .addresses(Set.of(address3, address2))
                .build();
        personRepository.saveAll(Set.of(rick, morty, summer, beth, jerry)).subscribe();
    }

}