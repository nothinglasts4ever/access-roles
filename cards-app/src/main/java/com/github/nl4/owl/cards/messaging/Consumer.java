package com.github.nl4.owl.cards.messaging;

import com.github.nl4.owl.cards.domain.Card;
import com.github.nl4.owl.cards.repo.CardRepository;
import com.github.nl4.owl.common.api.AccessRoleUpdated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class Consumer {

    private final CardRepository repository;

    @KafkaListener(topics = "${app.topic}")
    public void listen(Message<AccessRoleUpdated> message) throws Exception {
        AccessRoleUpdated data = message.getPayload();
        UUID accessRoleId = data.getId();
        log.info("Received update of access role with id [{}]", accessRoleId);

        repository.findAllByAccessRoles_AccessRoleId(accessRoleId)
                .map(card -> updateRolesInfo(data, accessRoleId, card))
                .flatMap(repository::save)
                .subscribe();
    }

    private Card updateRolesInfo(AccessRoleUpdated data, UUID accessRoleId, Card card) {
        card.getAccessRoles()
                .stream()
                .filter(role -> Objects.equals(role.getAccessRoleId(), accessRoleId))
                .forEach(role -> {
                    role.setExpiration(data.getEndTime());
                    role.setLocationName(data.getLocationName());
                });
        return card;
    }

}