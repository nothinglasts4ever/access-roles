package com.github.nl4.owl.persons.messaging;

import com.github.nl4.owl.common.messaging.MessagingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

    @Value("${app.topic.person}")
    private String personTopic;

    private final KafkaTemplate<String, MessagingEvent> template;

    public void sendToPersonTopic(MessagingEvent message) {
        sendMessage(message, personTopic);
    }

    private void sendMessage(MessagingEvent message, String topic) {
        var msg = MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        template.send(msg);
    }

}