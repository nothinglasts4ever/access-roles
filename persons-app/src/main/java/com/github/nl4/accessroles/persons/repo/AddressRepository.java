package com.github.nl4.accessroles.persons.repo;

import com.github.nl4.accessroles.persons.domain.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}