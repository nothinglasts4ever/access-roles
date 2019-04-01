package com.github.nl4.owl.cards.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    private String id;
    private String barcode;
    private PersonInfo personInfo;
    private Set<AccessRoleInfo> accessRoles;
    private String createdBy;
}