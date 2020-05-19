package com.github.nl4.owl.roles.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
public class Location implements Serializable {

    @Id
    private UUID id;
    @NotNull
    private String name;

}