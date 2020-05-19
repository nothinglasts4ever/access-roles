package com.github.nl4.owl.roles.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class LocationDto {

    private UUID id;
    @NotBlank
    private String name;

}