package com.github.nl4.owl.common.messaging;

import com.github.nl4.owl.common.data.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PersonUpdated extends MessagingEvent {
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthday;
    private Set<String> addresses;
}