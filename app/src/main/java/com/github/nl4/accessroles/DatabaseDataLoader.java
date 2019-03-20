package com.github.nl4.accessroles;

import com.github.nl4.accessroles.domain.AccessRole;
import com.github.nl4.accessroles.domain.Location;
import com.github.nl4.accessroles.repo.AccessRoleRepository;
import com.github.nl4.accessroles.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatabaseDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final LocationRepository locationRepository;
    private final AccessRoleRepository accessRoleRepository;

    @Autowired
    public DatabaseDataLoader(LocationRepository locationRepository, AccessRoleRepository accessRoleRepository) {
        this.locationRepository = locationRepository;
        this.accessRoleRepository = accessRoleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        var gazor = locationRepository.save(Location.builder().name("Gazorpazorp").build());
        var cronen = locationRepository.save(Location.builder().name("Cronenberg World").build());
        var earth = locationRepository.save(Location.builder().name("Earth").build());
        var pluto = locationRepository.save(Location.builder().name("Pluto").build());

        var rickId = 1L;
        var mortyId = 2L;
        var summerId = 3L;
        var bethId = 4L;
        var jerryId = 5L;

        accessRoleRepository.save(AccessRole.builder().personId(rickId).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(30)).build());
        accessRoleRepository.save(AccessRole.builder().personId(rickId).location(gazor).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(10)).build());
        accessRoleRepository.save(AccessRole.builder().personId(rickId).location(pluto).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(3)).build());
        accessRoleRepository.save(AccessRole.builder().personId(rickId).location(cronen).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(7)).build());
        accessRoleRepository.save(AccessRole.builder().personId(mortyId).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(70)).build());
        accessRoleRepository.save(AccessRole.builder().personId(mortyId).location(gazor).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(10)).build());
        accessRoleRepository.save(AccessRole.builder().personId(summerId).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(70)).build());
        accessRoleRepository.save(AccessRole.builder().personId(bethId).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(50)).build());
        accessRoleRepository.save(AccessRole.builder().personId(jerryId).location(earth).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(50)).build());
        accessRoleRepository.save(AccessRole.builder().personId(jerryId).location(pluto).start(LocalDateTime.now()).end(LocalDateTime.now().plusYears(100)).build());
    }

}