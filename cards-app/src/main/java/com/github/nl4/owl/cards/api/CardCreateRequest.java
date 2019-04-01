package com.github.nl4.owl.cards.api;

import com.github.nl4.owl.cards.domain.AccessRoleInfo;
import com.github.nl4.owl.cards.domain.PersonInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateRequest {
    @NotNull
    private PersonInfo personInfo;
    @NotNull
    private Set<AccessRoleInfo> accessRoles;
}