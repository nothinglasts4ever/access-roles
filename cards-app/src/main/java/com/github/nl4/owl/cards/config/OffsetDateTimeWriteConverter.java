package com.github.nl4.owl.cards.config;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.util.Date;


public class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, Date> {

    @Override
    public Date convert(OffsetDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

}