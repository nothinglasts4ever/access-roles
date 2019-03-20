package com.github.nl4.accessroles;

import com.github.nl4.accessroles.common.data.Gender;
import com.github.nl4.accessroles.domain.Person;
import com.github.nl4.accessroles.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final PersonRepository personRepository;

    @Autowired
    public DatabaseDataLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        personRepository.save(Person.builder().firstName("Rick").lastName("Sanchez").gender(Gender.MALE).birthday(LocalDate.now().minusYears(70)).build());
        personRepository.save(Person.builder().firstName("Morty").lastName("Smith").gender(Gender.MALE).birthday(LocalDate.now().minusYears(14)).build());
        personRepository.save(Person.builder().firstName("Summer").lastName("Smith").gender(Gender.FEMALE).birthday(LocalDate.now().minusYears(17)).build());
        personRepository.save(Person.builder().firstName("Beth").lastName("Smith").gender(Gender.FEMALE).birthday(LocalDate.now().minusYears(34)).build());
        personRepository.save(Person.builder().firstName("Jerry").lastName("Smith").gender(Gender.MALE).birthday(LocalDate.now().minusYears(34)).build());
    }

}