package com.github.nl4.owl.roles.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
public class AccessRole implements Serializable {

    @Id
    private UUID id;
    @NotNull
    private String personId;
    @ManyToOne
    @NotNull
    private Location location;
    @NotNull
    private OffsetDateTime start;
    @NotNull
    private OffsetDateTime end;
    private String createdBy;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

}