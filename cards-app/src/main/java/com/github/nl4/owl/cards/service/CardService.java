package com.github.nl4.owl.cards.service;

import com.github.nl4.owl.cards.api.CardCreateRequest;
import com.github.nl4.owl.cards.api.CardDto;
import com.github.nl4.owl.cards.config.CardMapper;
import com.github.nl4.owl.cards.domain.Card;
import com.github.nl4.owl.cards.domain.PersonInfo;
import com.github.nl4.owl.cards.repo.CardRepository;
import com.github.nl4.owl.cards.util.CardUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper mapper;

    public Flux<CardDto> getAllCards(boolean active) {
        return cardRepository.findAllByActive(active)
                .map(mapper::toCardDto);
    }

    public Mono<CardDto> getCard(UUID id) {
        return cardRepository.findByIdAndActiveIsTrue(id)
                .map(mapper::toCardDto);
    }

    public Mono<CardDto> createCard(CardCreateRequest request) {
        var accessRoles = mapper.toAccessRoleSet(request.getAccessRoles());
        var personInfo = PersonInfo.builder()
                .personId(request.getPersonId())
                .personName(request.getPersonName())
                .personDetails(request.getPersonDetails())
                .build();
        var card = Card.builder()
                .id(UUID.randomUUID())
                .personInfo(personInfo)
                .accessRoles(accessRoles)
                .barcode(CardUtil.generateBarcode(personInfo, accessRoles))
                .active(true)
                .build();

        return cardRepository.save(card)
                .map(mapper::toCardDto);
    }

    public Mono<CardDto> softDelete(UUID id) {
        return cardRepository.findByIdAndActiveIsTrue(id)
                .map(card -> {
                    card.setActive(false);
                    return card;
                })
                .flatMap(cardRepository::save)
                .map(mapper::toCardDto);
    }

}