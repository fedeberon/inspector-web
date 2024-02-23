package com.ideaas.services.bean;

import com.ideaas.services.enums.ReservationState;
import lombok.*;

/**
 * This DTO represents the data of a single reservation in a single day.
 */
@Data
@AllArgsConstructor
public class MapReservationPerDayDTO {

    /**
     * The {@link com.ideaas.services.domain.MapCliente#getNombre name} of
     * the client of the reservations campaign.
     */
    private String client;

    /**
     * The {@link com.ideaas.services.domain.MapCampaign#getName name} of
     * the reservations campaign.
     */
    private String campaign;

    /**
     * The {@link ReservationState reservation status}.
     */
    private ReservationState reservationState;
}
