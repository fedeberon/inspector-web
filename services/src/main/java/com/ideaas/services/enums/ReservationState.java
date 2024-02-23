package com.ideaas.services.enums;

import lombok.ToString;

import java.util.stream.Stream;

/**
 * This enum represents the possible states through which the reservations
 * of a location can pass, in a given date range.
 */
@ToString
public enum ReservationState {
    /**
     * No changes, it is used for when is updating a reservation, but
     * want to keep the previous state, no matter what it was
     */
    NOT_CHANGE(    0L, "Mantener estado actual"),

    /**
     * Represents a location without reservations, and therefore available
     */
    AVAILABLE(    1L, "Disponible"),

    /**
     * A reservation pending to be confirmed. There may be multiple unconfirmed
     * reservations for the same location with the same date range.
     */
    NOT_CONFIRMED(2L, "A confirmar"),

    /**
     * A canceled reservation for a location. They are all the reservations pending
     * to be confirmed whose dates overlap with those of a confirmed reservation,
     * and that therefore became canceled
     */
    CANCELLED(    3L, "Cancelada"),

    /**
     * A confirmed reservation of a location. There can only be one confirmed reservation
     * in a range of dates
     */
    CONFIRMED(    4L, "Confirmada");

    private Long code;
    private String description;

    private ReservationState(Long code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public Long getCode() {
        return code;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get an instance of the enum via the given enum {@link #getCode() code}.
     *
     * @param code the code
     * @return the reservation state
     */
    public static ReservationState of(Long code) {
        return Stream.of(ReservationState.values())
                .filter(p -> p.getCode() == code)
                .findFirst()
                .orElse(null);
    }
}
