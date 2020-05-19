package com.github.nl4.owl.cards.config;

import com.github.nl4.owl.cards.api.AccessRoleDto;
import com.github.nl4.owl.cards.api.CardDto;
import com.github.nl4.owl.cards.domain.AccessRoleInfo;
import com.github.nl4.owl.cards.domain.Card;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto toCardDto(Card card);

    Set<AccessRoleInfo> toAccessRoleSet(Set<AccessRoleDto> roles);

}