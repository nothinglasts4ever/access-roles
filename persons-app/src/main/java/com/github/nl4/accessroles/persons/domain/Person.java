package com.github.nl4.accessroles.persons.domain;

import com.github.nl4.accessroles.common.data.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthday;
    private Set<Address> addresses;
}