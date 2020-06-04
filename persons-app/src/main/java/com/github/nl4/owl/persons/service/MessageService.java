package com.github.nl4.owl.persons.service;

import com.github.nl4.owl.common.messaging.MessageType;
import com.github.nl4.owl.common.messaging.PersonUpdated;
import com.github.nl4.owl.persons.api.AddressDto;
import com.github.nl4.owl.persons.api.PersonDto;
import com.github.nl4.owl.persons.messaging.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final Producer producer;

    public void sendPersonUpdated(PersonDto person) {
        var message = new PersonUpdated();
        message.setId(person.getId());
        message.setType(MessageType.PERSON_UPDATED);
        message.setFirstName(person.getFirstName());
        message.setLastName(person.getLastName());
        message.setBirthday(person.getBirthday());
        message.setGender(person.getGender());
        message.setAddresses(convertAddresses(person.getAddresses()));

        producer.sendToPersonTopic(message);
    }

    public void sendPersonDeleted(UUID id) {
        producer.sendToPersonTopic(PersonUpdated.createPersonDeletedEvent(id));
    }

    private Set<String> convertAddresses(Set<AddressDto> addresses) {
        return addresses.stream()
                .map(adr -> adr.getCity() + ", " + adr.getBuilding() + " " + adr.getStreet() + " st., apt. " + adr.getApartment())
                .collect(Collectors.toSet());
    }

}