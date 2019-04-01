package com.github.nl4.owl.cards.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessRoleInfo {
    private Long accessRoleId;
    private Long locationId;
    private String locationName;
    private LocalDateTime expiration;
}