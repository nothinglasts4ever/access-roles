package com.github.nl4.owl.roles.controller;

import com.github.nl4.owl.roles.api.LocationDto;
import com.github.nl4.owl.roles.service.AccessRoleService;
import com.github.nl4.owl.roles.service.LocationService;
import com.github.nl4.owl.roles.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationService locationService;
    private final AccessRoleService accessRolesService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<Collection<LocationDto>> allLocations() {
        var locations = locationService.allLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable UUID id) {
        var location = locationService.getLocation(id);
        return ResponseEntity.ok(location);
    }

    @PostMapping
    public ResponseEntity<Void> createLocation(@RequestBody @Valid LocationDto location, HttpServletRequest request) {
        var createdLocation = locationService.createLocation(location);
        var uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/locations/{id}")
                .buildAndExpand(createdLocation.getId())
                .toUri();
        log.info("Location created: {}", uri);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLocation(@PathVariable UUID id, @RequestBody @Valid LocationDto location) {
        LocationDto updatedLocation = locationService.updateLocation(location, id);
        messageService.sendLocationUpdated(updatedLocation);
        log.info("Location with id [{}] updated", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID id) {
        locationService.deleteLocation(id);
        accessRolesService.deleteAccessRolesForLocation(id);
        messageService.sendLocationDeleted(id);
        log.info("Location with id [{}] removed", id);
        return ResponseEntity.noContent().build();
    }

}