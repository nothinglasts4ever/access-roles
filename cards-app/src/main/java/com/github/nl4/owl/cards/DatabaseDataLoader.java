package com.github.nl4.owl.cards;

import com.github.nl4.owl.cards.domain.AccessRoleInfo;
import com.github.nl4.owl.cards.domain.Card;
import com.github.nl4.owl.cards.domain.PersonInfo;
import com.github.nl4.owl.cards.repo.CardRepository;
import com.github.nl4.owl.cards.util.CardUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseDataLoader {

    private final CardRepository cardRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        createCards();
    }

    void createCards() {
        var rick = PersonInfo.builder()
                .personId(UUID.fromString("9f0011f5-72d6-4275-8555-15e350362828"))
                .personName("Rick Sanchez")
                .personDetails("Gender: M, Age: 70")
                .build();

        var role1 = AccessRoleInfo.builder()
                .accessRoleId(UUID.fromString("373ea031-93da-46f0-b2d1-ebf6f851ddd7"))
                .locationId(UUID.fromString("5dda0159-ed5c-44d4-b5f7-efb68ffbe8f8"))
                .locationName("Gazorpazorp")
                .expiration(OffsetDateTime.now().plusYears(30))
                .build();
        var role2 = AccessRoleInfo.builder()
                .accessRoleId(UUID.fromString("f4cf559f-21bf-4818-82d3-3298fe482dc0"))
                .locationId(UUID.fromString("584c3365-bb4a-4a78-a2d6-d9b6d256dc19"))
                .locationName("Cronenberg World")
                .expiration(OffsetDateTime.now().plusYears(10))
                .build();

        var card = Card.builder()
                .barcode(CardUtil.generateBarcode(rick, Set.of(role1, role2)))
                .personInfo(rick)
                .accessRoles(Set.of(role1, role2))
                .build();

        cardRepository.save(card)
                .subscribe();
    }

}