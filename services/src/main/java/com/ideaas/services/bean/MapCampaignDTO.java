package com.ideaas.services.bean;

import com.ideaas.services.enums.ReservationState;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
public class MapCampaignDTO extends BasicLocationDTO{
    private Long campaignId;
    private String campaign;
    private Long clientId;
    private String client;
    private Long orderNumber;
    private Long id;

    /**
     * The earliest start date of all bookings in the campaign.
     */
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    /**
     * The highest end date of all bookings in the campaign.
     */
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate finishDate;

    /**
     * The status of the campaign, te status of the campaign is defined by the status
     * of all your reservations. If the campaign has at least one {@link ReservationState#NOT_CONFIRMED
     * unconfirmed reservation}, then it keeps that status, otherwise it keeps the status with the highest
     * {@link ReservationState#getCode code}.
     */
    private ReservationState reservationState;

    /**
     * The reservation state {@link ReservationState#getCode code}.
     */
    private Long reservationStateCode;

    /**
     * The total amount of all reservations, discounting {@link ReservationState
     * canceled reservations}.
     */
    private Long reservationAmount;

    /**
     * A boolean that indicate if the campaign contain reservation that are {@link ReservationState
     * canceled}.
     */
    private boolean hasCanceledReservations;

    /**
     * A boolean tha indicate if the current instance of the campaign is
     * being cloning.
     */
    private Boolean isCloning;

    /**
     * The {@link com.ideaas.services.domain.MapReservacion#getExhibir exhibir} of
     * the client of the reservations campaign.
     */
    private Boolean exhibir;

    public MapCampaignDTO(
            Long campaignId,
            String campaign,
            String client,
            LocalDate startDate,
            LocalDate finishDate,
            Long reservationtStateCode,
            Long reservationAmount,
            boolean hasCanceledReservations) {
        this.exhibir = false;
        this.campaignId = campaignId;
        this.client = client;
        this.campaign = campaign;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.reservationState = ReservationState.of(reservationtStateCode);
        this.reservationAmount = reservationAmount;
        this.hasCanceledReservations = hasCanceledReservations;
    }

    public MapCampaignDTO(
            Long campaignId,
            String campaignName,
            Long clientId,
            String clientName,
            LocalDate startDate,
            LocalDate finishDate,
            long amountReservations,
            Long reservationtStateCode,
            boolean hasCanceledReservations,
            Boolean exhibir,
            Long orderNumber,
            Long id) {
        this.campaignId = campaignId;
        this.campaign = campaignName;
        this.client = clientName;
        this.clientId = clientId;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.reservationAmount = amountReservations;
        this.reservationState = ReservationState.of(reservationtStateCode);
        this.hasCanceledReservations = hasCanceledReservations;
        this.exhibir = exhibir;
        this.orderNumber = orderNumber;
        this.id = id;
    }

        public MapCampaignDTO(
            Long campaignId,
            String campaignName,
            Long clientId,
            String clientName,
            LocalDate startDate,
            LocalDate finishDate,
            long amountReservations,
            Long reservationtStateCode,
            boolean hasCanceledReservations,
            Boolean exhibir) {
        this.exhibir = exhibir;
        this.campaignId = campaignId;
        this.campaign = campaignName;
        this.client = clientName;
        this.clientId = clientId;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.reservationAmount = amountReservations;
        this.reservationState = ReservationState.of(reservationtStateCode);
        this.hasCanceledReservations = hasCanceledReservations;
    }

    public MapCampaignDTO(
            Long campaignId,
            String city,
            String company,
            String element,
            String province,
            String client,
            String campaign,
            LocalDate startDate,
            LocalDate finishDate,
            ReservationState reservationtState,
            Long reservationAmount) {
        super(city, company, element, province);
        this.campaignId = campaignId;
        this.client = client;
        this.campaign = campaign;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.reservationState = reservationtState;
        this.reservationAmount = reservationAmount;
    }

    public ReservationState getReservationState() {
        if(Objects.nonNull(reservationState)) return reservationState;
        if(Objects.nonNull(reservationStateCode)) return ReservationState.of(reservationStateCode);
        return null;
    }
}
