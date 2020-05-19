package com.github.nl4.owl.cards.api;

import lombok.Data;

import java.util.UUID;

@Data
public class PersonDto {

    private UUID personId;
    private String personName;
    private String personDetails;

}