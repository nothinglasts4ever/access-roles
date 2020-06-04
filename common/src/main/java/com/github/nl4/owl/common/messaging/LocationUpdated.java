package com.github.nl4.owl.common.messaging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LocationUpdated extends MessagingEvent {
    private String locationName;
}