package com.github.nl4.accessroles.rest;

import com.github.nl4.accessroles.domain.Location;
import com.github.nl4.accessroles.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Location>> allLocations() {
        Iterable<Location> locations = locationService.allLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Location> getLocation(@PathVariable Long id) {
        Location location = locationService.getLocation(id);
        return ResponseEntity.ok(location);
    }

    @PostMapping
    public ResponseEntity<Void> createLocation(@RequestBody Location location, HttpServletRequest request) {
        Location createdLocation = locationService.createLocation(location);
        URI uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/locations/{id}")
                .buildAndExpand(createdLocation.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/id")
    public ResponseEntity<Void> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        locationService.updateLocation(location, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/id")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

}