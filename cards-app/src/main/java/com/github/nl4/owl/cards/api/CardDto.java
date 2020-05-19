package com.github.nl4.owl.cards.api;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class CardDto {

    private UUID id;
    private String barcode;
    private PersonDto personInfo;
    private Set<AccessRoleDto> accessRoles;
    private String createdBy;

}