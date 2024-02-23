package com.ideaas.services.exception;

import lombok.ToString;

/**
 * ReservationException is an exception that can be thrown while a
 * reservation is being modified.
 */
public class ReservationException extends RuntimeException
        implements Fault<ReservationException.Code> {

    /**
     * This enum defines the types of errors that can occur when a
     * reservation is being modified.
     */
    @ToString
    public enum Code {
        /**
         * When a client already has a reservation for the same location
         * with the same dates in the same campaign
         */
        OVERLAPPED_CAMPAIGN_RESERVATION,
        /**
         * When there is a {@link com.ideaas.services.enums.ReservationState confirmed}
         * reservation of the same location with the same dates in the same campaign
         */
        OVERLAPPED_CONFIRMED_RESERVATION,
    }

    private final Code code;

    public ReservationException(Code code) {
        this.code = code;
    }

    @Override
    public Code code() {
        return code;
    }
}
