package com.github.nl4.owl.roles.service;

import com.github.nl4.owl.roles.domain.Location;
import com.github.nl4.owl.roles.repo.AccessRoleRepository;
import com.github.nl4.owl.roles.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private final AccessRoleRepository accessRoleRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, AccessRoleRepository accessRoleRepository) {
        this.locationRepository = locationRepository;
        this.accessRoleRepository = accessRoleRepository;
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
        var dbLocation = getLocation(id);
        dbLocation.setName(location.getName());
        return locationRepository.save(dbLocation);
    }

    public void deleteLocation(Long id) {
        var location = getLocation(id);
        accessRoleRepository.deleteAccessRolesByLocation_Id(id);
        locationRepository.delete(location);
    }

}