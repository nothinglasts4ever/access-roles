package com.github.nl4.owl.cards.service;

import com.github.nl4.owl.cards.domain.AccessRoleInfo;
import com.github.nl4.owl.cards.domain.Card;
import com.github.nl4.owl.cards.repo.CardRepository;
import com.github.nl4.owl.cards.util.CardUtil;
import com.github.nl4.owl.common.messaging.AccessRoleUpdated;
import com.github.nl4.owl.common.messaging.LocationUpdated;
import com.github.nl4.owl.common.messaging.MessagingEvent;
import com.github.nl4.owl.common.messaging.PersonUpdated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private final CardRepository repository;

    public void updateCardsWithLocation(MessagingEvent event) {
        var data = (LocationUpdated) event;
        repository.findAllByAccessRoles_LocationIdAndActiveIsTrue(data.getId())
                .map(card -> updateLocationsInfo(data, card))
                .flatMap(repository::save)
                .subscribe();
    }

    public void deleteRolesWithLocation(MessagingEvent event) {
        var id = event.getId();
        repository.findAllByAccessRoles_LocationIdAndActiveIsTrue(id)
                .map(card -> removeAccessRolesWithLocationId(card, id))
                .flatMap(repository::save)
                .subscribe();
    }

    public void updateCardsWithRoles(MessagingEvent event) {
        var data = (AccessRoleUpdated) event;
        repository.findAllByAccessRoles_AccessRoleIdAndActiveIsTrue(data.getId())
                .map(card -> updateRolesInfo(data, card))
                .flatMap(repository::save)
                .subscribe();
    }

    public void deleteRoles(MessagingEvent event) {
        var id = event.getId();
        repository.findAllByAccessRoles_AccessRoleIdAndActiveIsTrue(id)
                .map(card -> removeAccessRolesWithId(card, id))
                .flatMap(repository::save)
                .subscribe();
    }

    public void updateCardsForPerson(MessagingEvent event) {
        var data = (PersonUpdated) event;
        repository.findAllByPersonInfo_PersonIdAndActiveIsTrue(data.getId())
                .map(card -> updatePersonInfo(data, card))
                .flatMap(repository::save)
                .subscribe();
    }

    public void deleteCards(MessagingEvent event) {
        var id = event.getId();
        repository.findAllByPersonInfo_PersonIdAndActiveIsTrue(id)
                .flatMap(this::softDelete)
                .subscribe();
    }

    private Card updateLocationsInfo(LocationUpdated data, Card card) {
        for (AccessRoleInfo role : card.getAccessRoles()) {
            if (Objects.equals(role.getLocationId(), data.getId())) {
                role.setLocationName(data.getLocationName());
            }
        }
        card.setBarcode(CardUtil.generateBarcode(card.getPersonInfo(), card.getAccessRoles()));
        return card;
    }

    private Card updateRolesInfo(AccessRoleUpdated data, Card card) {
        for (AccessRoleInfo role : card.getAccessRoles()) {
            if (Objects.equals(role.getAccessRoleId(), data.getId())) {
                role.setExpiration(data.getEndTime());
            }
        }
        return card;
    }

    private Card updatePersonInfo(PersonUpdated data, Card card) {
        var personInfo = card.getPersonInfo();
        personInfo.setPersonName(CardUtil.composePersonName(data.getFirstName(), data.getLastName()));
        personInfo.setPersonDetails(CardUtil.composePersonDetails(data.getGender(), data.getBirthday(), data.getAddresses()));
        card.setPersonInfo(personInfo);
        return card;
    }

    private Card removeAccessRolesWithLocationId(Card card, UUID id) {
        var roles = card.getAccessRoles()
                .stream()
                .filter(role -> !Objects.equals(role.getLocationId(), id))
                .collect(Collectors.toSet());
        card.setAccessRoles(roles);
        card.setBarcode(CardUtil.generateBarcode(card.getPersonInfo(), card.getAccessRoles()));
        return card;
    }

    private Card removeAccessRolesWithId(Card card, UUID id) {
        var roles = card.getAccessRoles()
                .stream()
                .filter(role -> !Objects.equals(role.getAccessRoleId(), id))
                .collect(Collectors.toSet());

        if (roles.size() > 0) {
            card.setAccessRoles(roles);
            card.setBarcode(CardUtil.generateBarcode(card.getPersonInfo(), card.getAccessRoles()));
        } else {
            card.setActive(false);
        }
        return card;
    }

    private Mono<Card> softDelete(Card card) {
        return repository.findByIdAndActiveIsTrue(card.getId())
                .map(c -> {
                    c.setActive(false);
                    return c;
                })
                .flatMap(repository::save);
    }

}