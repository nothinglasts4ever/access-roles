package com.github.nl4.owl.cards;

import com.github.nl4.owl.cards.domain.AccessRoleInfo;
import com.github.nl4.owl.cards.domain.Card;
import com.github.nl4.owl.cards.domain.PersonInfo;
import com.github.nl4.owl.cards.repo.CardRepository;
import com.github.nl4.owl.cards.util.CardUtil;
import com.github.nl4.owl.common.data.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
        var address1 = "Black, 234 41st st., apt. 2342";
        var address2 = "Yellow, 123 32nd st., apt. 9876";

        var rick = PersonInfo.builder()
                .personId(UUID.fromString("9f0011f5-72d6-4275-8555-15e350362828"))
                .personName(CardUtil.composePersonName("Rick", "Sanchez"))
                .personDetails(CardUtil.composePersonDetails(Gender.MALE, LocalDate.now().minusYears(70), Set.of(address1, address2)))
                .build();
        var morty = PersonInfo.builder()
                .personId(UUID.fromString("4ab0e982-a990-4e98-8c25-de6e025681a6"))
                .personName(CardUtil.composePersonName("Morty", "Smith"))
                .personDetails(CardUtil.composePersonDetails(Gender.MALE, LocalDate.now().minusYears(14), Set.of(address2)))
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
        var role3 = AccessRoleInfo.builder()
                .accessRoleId(UUID.fromString("4525b0a4-00ed-4779-aa09-24d920d53494"))
                .locationId(UUID.fromString("5e644a41-50ff-43e5-be85-26d6f9619b6b"))
                .locationName("Earth")
                .expiration(OffsetDateTime.now().plusYears(20))
                .build();
        var role4 = AccessRoleInfo.builder()
                .accessRoleId(UUID.fromString("d69a6cd2-2cb7-4c5a-ba37-bec87d1b2516"))
                .locationId(UUID.fromString("5dda0159-ed5c-44d4-b5f7-efb68ffbe8f8"))
                .locationName("Gazorpazorp")
                .expiration(OffsetDateTime.now().plusYears(40))
                .build();

        var card1 = Card.builder()
                .id(UUID.randomUUID())
                .barcode(CardUtil.generateBarcode(rick, Set.of(role1, role2)))
                .personInfo(rick)
                .accessRoles(Set.of(role1, role2))
                .active(true)
                .build();
        var card2 = Card.builder()
                .id(UUID.randomUUID())
                .barcode(CardUtil.generateBarcode(morty, Set.of(role3, role4)))
                .personInfo(morty)
                .accessRoles(Set.of(role3, role4))
                .active(true)
                .build();

        cardRepository.saveAll(Set.of(card1, card2))
                .subscribe();
    }

}