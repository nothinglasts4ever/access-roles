package com.github.nl4.accessroles.controller;

import com.github.nl4.accessroles.domain.Location;
import com.github.nl4.accessroles.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/locations")
@Slf4j
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Location>> allLocations() {
        var locations = locationService.allLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Location> getLocation(@PathVariable Long id) {
        var location = locationService.getLocation(id);
        return ResponseEntity.ok(location);
    }

    @PostMapping
    public ResponseEntity<Void> createLocation(@RequestBody Location location, HttpServletRequest request) {
        var createdLocation = locationService.createLocation(location);
        var uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/locations/{id}")
                .buildAndExpand(createdLocation.getId())
                .toUri();
        log.info("Location created: " + uri);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        locationService.updateLocation(location, id);
        log.info("Location with id [" + id + "] updated");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        log.info("Location with id [" + id + "] removed");
        return ResponseEntity.noContent().build();
    }

}