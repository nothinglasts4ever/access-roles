package com.github.nl4.owl.persons;

import com.github.nl4.owl.common.data.Gender;
import com.github.nl4.owl.persons.domain.Address;
import com.github.nl4.owl.persons.domain.Person;
import com.github.nl4.owl.persons.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseDataLoader {

    private final PersonRepository personRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        createPersons();
    }

    void createPersons() {
        var address1 = Address.builder().city("Black").street("41st Street").building(234).apartment(2342).build();
        var address2 = Address.builder().city("Yellow").street("32nd Street").building(123).apartment(9876).build();
        var address3 = Address.builder().city("Red").street("23rd Street").building(456).apartment(1).build();

        var rick = Person.builder()
                .id(UUID.fromString("9f0011f5-72d6-4275-8555-15e350362828"))
                .firstName("Rick")
                .lastName("Sanchez")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(70))
                .addresses(Set.of(address1, address2))
                .build();

        var morty = Person.builder()
                .id(UUID.fromString("4ab0e982-a990-4e98-8c25-de6e025681a6"))
                .firstName("Morty")
                .lastName("Smith")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(14))
                .addresses(Set.of(address2))
                .build();

        var summer = Person.builder()
                .id(UUID.fromString("23e841a0-4be0-4723-bbd5-21f8b6ee82af"))
                .firstName("Summer")
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.now().minusYears(17))
                .addresses(Set.of(address2))
                .build();

        var beth = Person.builder()
                .id(UUID.fromString("34afaaaa-3eca-4245-a8df-185d8af54fce"))
                .firstName("Beth")
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.now().minusYears(34))
                .addresses(Set.of(address2))
                .build();

        var jerry = Person.builder()
                .id(UUID.fromString("fd9da139-7bc5-4885-982b-d107732f1cc1"))
                .firstName("Jerry")
                .lastName("Smith")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(34))
                .addresses(Set.of(address3, address2))
                .build();

        personRepository.saveAll(Set.of(rick, morty, summer, beth, jerry))
                .subscribe();
    }

}