package com.github.nl4.owl.cards.repo;

import com.github.nl4.owl.cards.domain.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CardRepository extends ReactiveMongoRepository<Card, UUID> {

    Flux<Card> findAllByActive(boolean active);

    Mono<Card> findByIdAndActiveIsTrue(UUID id);

    Flux<Card> findAllByAccessRoles_AccessRoleIdAndActiveIsTrue(UUID accessRoleId);

    Flux<Card> findAllByAccessRoles_LocationIdAndActiveIsTrue(UUID locationId);

    Flux<Card> findAllByPersonInfo_PersonIdAndActiveIsTrue(UUID personId);

}