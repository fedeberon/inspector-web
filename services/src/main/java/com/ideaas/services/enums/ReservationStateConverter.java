package com.ideaas.services.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ReservationStateConverter implements AttributeConverter<ReservationState, Long> {

    @Override
    public Long convertToDatabaseColumn(ReservationState reservationtState) {
        if (reservationtState == null) {
            return null;
        }
        return reservationtState.getCode();
    }

    @Override
    public ReservationState convertToEntityAttribute(Long code) {
        if (code == null) {
            return null;
        }

        return Stream.of(ReservationState.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
