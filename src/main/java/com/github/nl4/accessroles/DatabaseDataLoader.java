package com.github.nl4.accessroles;

import com.github.nl4.accessroles.domain.AccessRole;
import com.github.nl4.accessroles.domain.Location;
import com.github.nl4.accessroles.domain.Person;
import com.github.nl4.accessroles.data.Gender;
import com.github.nl4.accessroles.repo.AccessRoleRepository;
import com.github.nl4.accessroles.repo.LocationRepository;
import com.github.nl4.accessroles.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DatabaseDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final PersonRepository personRepository;
    private final LocationRepository locationRepository;
    private final AccessRoleRepository accessRoleRepository;

    @Autowired
    public DatabaseDataLoader(PersonRepository personRepository, LocationRepository locationRepository, AccessRoleRepository accessRoleRepository) {
        this.personRepository = personRepository;
        this.locationRepository = locationRepository;
        this.accessRoleRepository = accessRoleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Person person = Person.builder()
                .firstName("Rick")
                .lastName("Sanchez")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(70))
                .build();
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

        Location location = Location.builder()
                .name("Gazorpazorp")
                .build();
        locationRepository.save(location);
        locationRepository.findAll().forEach(System.out::println);

        AccessRole accessRole = AccessRole.builder()
                .person(person)
                .location(location)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusWeeks(3))
                .build();
        accessRoleRepository.save(accessRole);
        accessRoleRepository.findAll().forEach(System.out::println);
    }

}