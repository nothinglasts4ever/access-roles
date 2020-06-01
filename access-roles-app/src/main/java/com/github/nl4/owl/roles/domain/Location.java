package com.github.nl4.owl.roles.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class Location {

    @Id
    private UUID id;
    @NotNull
    private String name;

}