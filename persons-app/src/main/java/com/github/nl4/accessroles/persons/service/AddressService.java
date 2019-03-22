package com.github.nl4.accessroles.persons.service;

import com.github.nl4.accessroles.persons.domain.Address;
import com.github.nl4.accessroles.persons.repo.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Iterable<Address> allAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddress(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find address with id [" + id + "]"));
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address, Long id) {
        getAddress(id);
        address.setId(id);
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        var address = getAddress(id);
        addressRepository.delete(address);
    }

}