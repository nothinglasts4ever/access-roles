package com.github.nl4.owl.common.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AccessRoleUpdated {
    private UUID id;
    private String locationName;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
}