package com.github.nl4.owl.roles.messaging;

import com.github.nl4.owl.common.api.AccessRoleUpdated;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

    @Value("${app.topic}")
    private String topic;

    private final KafkaTemplate<String, AccessRoleUpdated> template;

    public void sendMessage(AccessRoleUpdated message) {
        var msg = MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        template.send(msg);
    }

}