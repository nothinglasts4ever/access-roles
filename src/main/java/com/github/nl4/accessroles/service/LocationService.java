package com.github.nl4.accessroles.service;

import com.github.nl4.accessroles.domain.Location;
import com.github.nl4.accessroles.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Iterable<Location> allLocations() {
        return locationRepository.findAll();
    }

    public Location getLocation(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find location with id [" + id + "]"));
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Location location, Long id) {
        Location dbLocation = getLocation(id);
        dbLocation.setName(location.getName());
        return locationRepository.save(dbLocation);
    }

    public void deleteLocation(Long id) {
        Location location = getLocation(id);
        locationRepository.delete(location);
    }

}