package com.github.nl4.owl.persons.api;

import com.github.nl4.owl.common.data.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class PersonDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthday;
    private Set<AddressDto> addresses;

}