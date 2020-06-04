package com.github.nl4.owl.common.messaging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AccessRoleUpdated extends MessagingEvent {
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
}