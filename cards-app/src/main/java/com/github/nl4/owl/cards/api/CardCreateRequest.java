package com.github.nl4.owl.cards.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Data
public class CardCreateRequest {

    @NotBlank(message = "Person id is not specified")
    private UUID personId;
    private String personName;
    private String personDetails;
    @NotEmpty(message = "Person has no active access roles")
    private Set<AccessRoleDto> accessRoles;

}