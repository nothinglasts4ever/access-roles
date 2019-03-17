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
import java.util.Arrays;

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
        Person rick = Person.builder()
                .firstName("Rick")
                .lastName("Sanchez")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(70))
                .build();
        Person morty = Person.builder()
                .firstName("Morty")
                .lastName("Smith")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(14))
                .build();
        Person summer = Person.builder()
                .firstName("Summer")
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.now().minusYears(17))
                .build();
        Person beth = Person.builder()
                .firstName("Beth")
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .birthday(LocalDate.now().minusYears(34))
                .build();
        Person jerry = Person.builder()
                .firstName("Jerry")
                .lastName("Smith")
                .gender(Gender.MALE)
                .birthday(LocalDate.now().minusYears(34))
                .build();
        personRepository.saveAll(Arrays.asList(rick, morty, summer, jerry, beth));

        Location gazor = Location.builder()
                .name("Gazorpazorp")
                .build();
        Location cronen = Location.builder()
                .name("Cronenberg World")
                .build();
        Location earth = Location.builder()
                .name("Earth")
                .build();
        Location pluto = Location.builder()
                .name("Pluto")
                .build();
        locationRepository.saveAll(Arrays.asList(gazor, cronen, earth, pluto));

        accessRoleRepository.save(AccessRole.builder().person(rick).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(30)).build());
        accessRoleRepository.save(AccessRole.builder().person(rick).location(gazor).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(10)).build());
        accessRoleRepository.save(AccessRole.builder().person(rick).location(pluto).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(3)).build());
        accessRoleRepository.save(AccessRole.builder().person(rick).location(cronen).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(7)).build());
        accessRoleRepository.save(AccessRole.builder().person(morty).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(70)).build());
        accessRoleRepository.save(AccessRole.builder().person(morty).location(gazor).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(10)).build());
        accessRoleRepository.save(AccessRole.builder().person(summer).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(70)).build());
        accessRoleRepository.save(AccessRole.builder().person(beth).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(50)).build());
        accessRoleRepository.save(AccessRole.builder().person(jerry).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(50)).build());
        accessRoleRepository.save(AccessRole.builder().person(jerry).location(pluto).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(100)).build());
    }

}