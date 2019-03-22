package com.github.nl4.accessroles.persons.controller;

import com.github.nl4.accessroles.persons.domain.Address;
import com.github.nl4.accessroles.persons.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/addresses")
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Address>> allAddresses() {
        var addresses = addressService.allAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        var address = addressService.getAddress(id);
        return ResponseEntity.ok(address);
    }

    @PostMapping
    public ResponseEntity<Void> createAddress(@RequestBody Address address, HttpServletRequest request) {
        var createdAddress = addressService.createAddress(address);
        var uri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/addresses/{id}")
                .buildAndExpand(createdAddress.getId())
                .toUri();
        log.info("Address created: " + uri);
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        addressService.updateAddress(address, id);
        log.info("Address with id [" + id + "] updated");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        log.info("Address with id [" + id + "] removed");
        return ResponseEntity.noContent().build();
    }

}