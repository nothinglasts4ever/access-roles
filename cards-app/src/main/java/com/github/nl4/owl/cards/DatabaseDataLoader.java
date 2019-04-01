package com.github.nl4.owl.cards;

import com.github.nl4.owl.cards.domain.AccessRoleInfo;
import com.github.nl4.owl.cards.domain.Card;
import com.github.nl4.owl.cards.domain.PersonInfo;
import com.github.nl4.owl.cards.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DatabaseDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CardRepository cardRepository;

    @Autowired
    public DatabaseDataLoader(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        var rick = PersonInfo.builder()
                .personId("1")
                .personName("Rick Sanchez")
                .personDetails("Gender: M, Age: 70")
                .build();
        var role1 = AccessRoleInfo.builder()
                .accessRoleId(10L)
                .locationId(1L)
                .locationName("Gazorpazorp")
                .expiration(LocalDateTime.now().plusYears(30))
                .build();
        var role2 = AccessRoleInfo.builder()
                .accessRoleId(11L)
                .locationId(2L)
                .locationName("Cronenberg World")
                .expiration(LocalDateTime.now().plusYears(10))
                .build();
        var card = Card.builder()
                .barcode("MToyOjEwOjE6MTE6Mg==")
                .personInfo(rick)
                .accessRoles(Set.of(role1, role2))
                .build();
        cardRepository.save(card).subscribe();
    }

}