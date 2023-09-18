package com.melitaltd.config;

import org.modelmapper.AbstractConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter extends AbstractConverter<String, LocalDateTime> {
    @Override
    protected LocalDateTime convert(String source) {
        // Assuming the source string is in ISO 8601 format (e.g., "2023-09-18T12:34:56")
        return LocalDateTime.parse(source);
    }
}
