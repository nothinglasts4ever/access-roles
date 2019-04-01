package com.github.nl4.owl.cards.controller;

import com.github.nl4.owl.cards.api.CardCreateRequest;
import com.github.nl4.owl.cards.domain.Card;
import com.github.nl4.owl.cards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
public class CardHandler {

    private final CardService cardService;

    @Autowired
    public CardHandler(CardService cardService) {
        this.cardService = cardService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(cardService.getAllCards(), Card.class));
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        final String id = request.pathVariable("id");
        final Mono<Card> card = cardService.getCard(id);
        final Mono<ServerResponse> successResponse = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(card, Card.class));
        return card
                .flatMap(c -> successResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> post(ServerRequest request) {
        final Mono<CardCreateRequest> cardRequest = request.bodyToMono(CardCreateRequest.class);
        return ServerResponse.created(UriComponentsBuilder.fromPath("cards/" + "id").build().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(cardRequest.flatMap(c -> cardService.createCard(c.getPersonInfo(), c.getAccessRoles())), Card.class));
    }

}