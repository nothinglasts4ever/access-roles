package com.github.nl4.owl.cards.messaging;

import com.github.nl4.owl.cards.service.UpdateService;
import com.github.nl4.owl.common.messaging.MessageType;
import com.github.nl4.owl.common.messaging.MessagingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Consumer {

    private final UpdateService updateService;

    @KafkaListener(topics = "${app.topic.location}")
    public void listenLocationUpdates(Message<MessagingEvent> message) throws Exception {
        var data = message.getPayload();
        var type = data.getType();
        log.info("Received message [{}] for location [{}]", type, data.getId());

        if (type == MessageType.LOCATION_UPDATED) {
            updateService.updateCardsWithLocation(data);
        } else if (type == MessageType.LOCATION_DELETED) {
            updateService.deleteRolesWithLocation(data);
        }
    }

    @KafkaListener(topics = "${app.topic.access-role}")
    public void listenAccessRoleUpdates(Message<MessagingEvent> message) throws Exception {
        var data = message.getPayload();
        var type = data.getType();
        log.info("Received message [{}] for access role [{}]", type, data.getId());

        if (type == MessageType.ACCESS_ROLE_UPDATED) {
            updateService.updateCardsWithRoles(data);
        } else if (type == MessageType.ACCESS_ROLE_DELETED) {
            updateService.deleteRoles(data);
        }
    }

    @KafkaListener(topics = "${app.topic.person}")
    public void listenPersonUpdates(Message<MessagingEvent> message) throws Exception {
        var data = message.getPayload();
        var type = data.getType();
        log.info("Received message [{}] for person [{}]", type, data.getId());

        if (type == MessageType.PERSON_UPDATED) {
            updateService.updateCardsForPerson(data);
        } else if (type == MessageType.PERSON_DELETED) {
            updateService.deleteCards(data);
        }
    }

}