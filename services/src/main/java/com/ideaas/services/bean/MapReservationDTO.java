package com.ideaas.services.bean;

import com.ideaas.services.enums.ReservationState;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * This DTO represents a view of a reservation of a location.
 */
@Data
public class MapReservationDTO extends LocationDTO{
    /**
     * The reservation id.
     */
    private Long reservationId;

    /**
     * The location address.
     */
    private String direction;

    /**
     * The reservation status.
     */
    private ReservationState reservationState;

    private Boolean exhibir;

    /**
     * The reservation campaign client.
     */
    private String client;

    /**
     * The reservation campaign.
     */
    private String campaign;
    @DateTimeFormat(pattern = "dd-MM-yyyy")

    /**
     * The reservation start date.
     */
    private LocalDate startDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")

    /**
     * The reservation finish date.
     */
    private LocalDate finishDate;

    /**
     * The reservation status code.
     */
    private Long reservationStateCode;

    /**
     * The latitude of the booked location.
     */
    private BigDecimal latitude;

    /**
     * The longitude of the booked location.
     */
    private BigDecimal longitude;

    /**
     * The number of advertising elements of the booked location.
     */
    private Long amount;

    private Long ordenNumber;

    public MapReservationDTO() {}

    public MapReservationDTO(String city, String company, String element, String province, String referenceId, Long id, String direction, ReservationState reservationState, String campaign, String client, LocalDate startDate, LocalDate finishDate, BigDecimal latitude, BigDecimal longitude, Long reservationId, Long amount, Boolean exhibir, Long ordenNumber) {
        super(id, company, direction, city, element, province, referenceId);
        this.direction        = direction;
        this.reservationState = reservationState;
        this.campaign         = campaign;
        this.client           = client;
        this.startDate        = startDate;
        this.finishDate       = finishDate;
        this.latitude         = latitude;
        this.longitude        = longitude;
        this.reservationId    = reservationId;
        this.exhibir = exhibir;
        this.amount           = amount;
        this.ordenNumber        = ordenNumber;
    }

    public ReservationState getReservationState() {
        if(Objects.nonNull(reservationState)) return reservationState;
        if(Objects.nonNull(reservationStateCode)) return ReservationState.of(reservationStateCode);
        return null;
    }
}