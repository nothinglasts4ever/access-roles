package com.github.nl4.accessroles.persons.domain;

import com.github.nl4.accessroles.common.data.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    private LocalDate birthday;
    @ManyToMany
    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "address_id")})
    private Set<Address> addresses;
}