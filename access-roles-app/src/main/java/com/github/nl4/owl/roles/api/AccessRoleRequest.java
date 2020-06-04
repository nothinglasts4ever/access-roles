package com.github.nl4.owl.roles.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class AccessRoleRequest {

    @NotBlank
    private UUID personId;
    @NotNull
    private UUID locationId;
    @NotNull
    private OffsetDateTime startTime;
    @NotNull
    private OffsetDateTime endTime;

}