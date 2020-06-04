package com.github.nl4.owl.roles.service;

import com.github.nl4.owl.roles.api.LocationDto;
import com.github.nl4.owl.roles.config.AccessRoleMapper;
import com.github.nl4.owl.roles.domain.Location;
import com.github.nl4.owl.roles.repo.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final AccessRoleMapper mapper;

    @Transactional(readOnly = true)
    public Collection<LocationDto> allLocations() {
        return mapper.toLocationDtoSet(locationRepository.findAll());
    }

    @Transactional(readOnly = true)
    public LocationDto getLocation(UUID id) {
        return mapper.toLocationDto(doGetLocation(id));
    }

    @Transactional
    public LocationDto createLocation(LocationDto location) {
        var entity = mapper.toLocation(location);
        entity.setId(UUID.randomUUID());

        return mapper.toLocationDto(locationRepository.save(entity));
    }

    @Transactional
    public LocationDto updateLocation(LocationDto location, UUID id) {
        var dbLocation = doGetLocation(id);
        dbLocation.setName(location.getName());

        return mapper.toLocationDto(dbLocation);
    }

    @Transactional
    public void deleteLocation(UUID id) {
        var location = doGetLocation(id);
        location.setDeletedAt(OffsetDateTime.now());
    }

    private Location doGetLocation(UUID id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find location with id [" + id + "]"));
    }

}