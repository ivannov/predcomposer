package com.nosoftskills.predcomposer.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Ivan St. Ivanov
 */
@Converter(autoApply = true)
public class DatesConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp date) {
        return date.toLocalDateTime();
    }
}
