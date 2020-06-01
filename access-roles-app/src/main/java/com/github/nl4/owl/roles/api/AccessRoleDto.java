package com.github.nl4.owl.roles.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class AccessRoleDto {

    private UUID id;
    @NotBlank
    private String personId;
    @NotNull
    private LocationDto location;
    @NotNull
    private OffsetDateTime startTime;
    @NotNull
    private OffsetDateTime endTime;
    private String createdBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}