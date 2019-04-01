package com.github.nl4.owl.cards.controller;

import com.github.nl4.owl.cards.api.CardCreateRequest;
import com.github.nl4.owl.cards.domain.Card;
import com.github.nl4.owl.cards.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cards")
@Slf4j
public class CardController {

    private final CardService personService;

    @Autowired
    public CardController(CardService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Flux<Card> getAllCards() {
        return personService.getAllCards();
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<Card>> getCard(@PathVariable String id) {
        return personService.getCard(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Card>> createCard(@RequestBody CardCreateRequest request) {
        if (request.getPersonInfo().getPersonId() == null || request.getAccessRoles().size() == 0) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
        return personService.createCard(request.getPersonInfo(), request.getAccessRoles())
                .map(p -> {
                    log.info("Card with barcode [" + p.getBarcode() + "] created");
                    return new ResponseEntity<>(p, HttpStatus.CREATED);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}