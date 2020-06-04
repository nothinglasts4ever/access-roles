package com.github.nl4.owl.roles.domain;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Where(clause = "deleted_at IS NULL")
@Data
public class Location {

    @Id
    private UUID id;
    @NotNull
    private String name;
    private OffsetDateTime deletedAt;

}