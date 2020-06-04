package com.github.nl4.owl.common.messaging;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MessagingEvent {

    private UUID id;
    private MessageType type;

    public static MessagingEvent createLocationDeletedEvent(UUID id) {
        return createMessagingEvent(id, MessageType.LOCATION_DELETED);
    }

    public static LocationUpdated createLocationUpdatedEvent(UUID id, String name) {
        var event = new LocationUpdated();
        event.setId(id);
        event.setType(MessageType.LOCATION_UPDATED);
        event.setLocationName(name);
        return event;
    }

    public static MessagingEvent createAccessRoleDeletedEvent(UUID id) {
        return createMessagingEvent(id, MessageType.ACCESS_ROLE_DELETED);
    }

    public static AccessRoleUpdated createAccessRoleUpdatedEvent(UUID id, OffsetDateTime startTime, OffsetDateTime endTime) {
        var event = new AccessRoleUpdated();
        event.setId(id);
        event.setType(MessageType.ACCESS_ROLE_UPDATED);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        return event;
    }

    public static MessagingEvent createPersonDeletedEvent(UUID id) {
        return createMessagingEvent(id, MessageType.PERSON_DELETED);
    }

    private static MessagingEvent createMessagingEvent(UUID id, MessageType locationDeleted) {
        var event = new MessagingEvent();
        event.setId(id);
        event.setType(locationDeleted);
        return event;
    }

}