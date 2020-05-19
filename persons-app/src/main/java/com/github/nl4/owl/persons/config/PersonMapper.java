package com.github.nl4.owl.persons.config;

import com.github.nl4.owl.persons.api.PersonDto;
import com.github.nl4.owl.persons.domain.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto toPersonDto(Person person);

    Person toPerson(PersonDto person);

}