package com.github.nl4.owl.roles.service;

import com.github.nl4.owl.common.api.AccessRoleUpdated;
import com.github.nl4.owl.roles.api.AccessRoleDto;
import com.github.nl4.owl.roles.messaging.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final Producer producer;

    // compare old and new
    public void sendMessage(UUID id, AccessRoleDto updated) {
        var message = new AccessRoleUpdated();
        message.setId(id);
        message.setLocationName(updated.getLocation() != null ? updated.getLocation().getName() : null);
        message.setStartTime(updated.getStartTime());
        message.setEndTime(updated.getEndTime());
        producer.sendMessage(message);
    }

}