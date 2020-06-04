package com.github.nl4.owl.roles.service;

import com.github.nl4.owl.common.messaging.MessagingEvent;
import com.github.nl4.owl.roles.api.AccessRoleDto;
import com.github.nl4.owl.roles.api.LocationDto;
import com.github.nl4.owl.roles.messaging.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final Producer producer;

    public void sendLocationUpdated(LocationDto location) {
        var message = MessagingEvent.createLocationUpdatedEvent(location.getId(), location.getName());
        producer.sendToLocationTopic(message);
    }

    public void sendLocationDeleted(UUID id) {
        producer.sendToLocationTopic(MessagingEvent.createLocationDeletedEvent(id));
    }

    public void sendAccessRoleUpdated(UUID id, AccessRoleDto role) {
        var message = MessagingEvent.createAccessRoleUpdatedEvent(id, role.getStartTime(), role.getEndTime());
        producer.sendToAccessRoleTopic(message);
    }

    public void sendAccessRoleDeleted(UUID id) {
        producer.sendToAccessRoleTopic(MessagingEvent.createAccessRoleDeletedEvent(id));
    }

}