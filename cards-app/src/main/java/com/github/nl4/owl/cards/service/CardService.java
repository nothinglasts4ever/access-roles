package com.github.nl4.owl.cards.service;

import com.github.nl4.owl.cards.domain.AccessRoleInfo;
import com.github.nl4.owl.cards.domain.Card;
import com.github.nl4.owl.cards.domain.PersonInfo;
import com.github.nl4.owl.cards.repo.CardRepository;
import com.github.nl4.owl.cards.util.CardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ValidationException;
import java.util.Set;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Flux<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Mono<Card> getCard(String id) {
        return cardRepository.findById(id);
    }

    public Mono<Card> createCard(PersonInfo personInfo, Set<AccessRoleInfo> accessRoles) {
        if (personInfo.getPersonId() == null) {
            throw new ValidationException("Person id is not specified");
        }
        if (accessRoles == null || accessRoles.size() == 0) {
            throw new ValidationException("Person has no active access roles");
        }
        var card = Card.builder()
                .personInfo(personInfo)
                .accessRoles(accessRoles)
                .barcode(CardUtil.generateBarcode(personInfo, accessRoles))
                .build();
        return cardRepository.save(card);
    }

}