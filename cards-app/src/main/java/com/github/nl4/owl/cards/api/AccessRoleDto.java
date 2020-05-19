package com.github.nl4.owl.cards.api;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class AccessRoleDto {

    @NotNull
    private UUID accessRoleId;
    @NotNull
    private UUID locationId;
    private String locationName;
    @NotNull
    private OffsetDateTime expiration;

}