package com.github.nl4.owl.cards.repo;

import com.github.nl4.owl.cards.domain.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CardRepository extends ReactiveMongoRepository<Card, String> {
}