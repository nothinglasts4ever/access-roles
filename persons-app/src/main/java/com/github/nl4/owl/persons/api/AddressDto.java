package com.github.nl4.owl.persons.api;

import lombok.Data;

@Data
public class AddressDto {

    private String city;
    private String street;
    private int building;
    private int apartment;

}