package com.github.nl4.owl.cards.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CardRouter {

    @Bean
    public RouterFunction<ServerResponse> route(CardHandler cardHandler) {
        RequestPredicate get = RequestPredicates.GET("/cards/{id}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        RequestPredicate getAll = RequestPredicates.GET("/cards")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        RequestPredicate post = RequestPredicates.POST("/cards")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON));

        return RouterFunctions.route(get, cardHandler::get)
                .andRoute(getAll, cardHandler::getAll)
                .andRoute(post, cardHandler::post);
    }

}