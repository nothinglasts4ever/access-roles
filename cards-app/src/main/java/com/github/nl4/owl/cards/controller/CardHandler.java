package com.github.nl4.owl.cards.controller;

import com.github.nl4.owl.cards.api.CardCreateRequest;
import com.github.nl4.owl.cards.api.CardDto;
import com.github.nl4.owl.cards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardHandler {

    private final CardService cardService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(cardService.getAllCards(), CardDto.class));
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        var id = request.pathVariable("id");
        var card = cardService.getCard(UUID.fromString(id));
        var successResponse = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(card, CardDto.class));

        return card
                .flatMap(c -> successResponse)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> post(ServerRequest request) {
        var location = UriComponentsBuilder.fromPath("cards/" + "id").build().toUri();
        var cardRequest = request.bodyToMono(CardCreateRequest.class)
                .flatMap(cardService::createCard);

        return ServerResponse.created(location)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(cardRequest, CardDto.class));
    }

}