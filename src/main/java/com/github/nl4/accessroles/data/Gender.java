package com.github.nl4.accessroles.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private String name;
}