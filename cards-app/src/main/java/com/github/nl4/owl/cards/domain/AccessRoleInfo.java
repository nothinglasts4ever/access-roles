package com.github.nl4.owl.cards.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.UUID;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessRoleInfo {

    private UUID accessRoleId;
    private UUID locationId;
    private String locationName;
    private OffsetDateTime expiration;

}