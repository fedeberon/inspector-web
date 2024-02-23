package com.ideaas.services.bean;

import com.ideaas.services.enums.ReservationState;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This DTO is based on the locations, and their reservations. A circuit is not
 * mapped to any entity in the data source. The circuits are a view that is
 * defined by all the possible combinations between some specific attributes
 * of the locations and their reservations.
 */
@Data
@NoArgsConstructor
public class MapCircuitDTO extends BasicLocationDTO{
    /**
     * The {@link com.ideaas.services.domain.MapCampaign#getId id} of
     * the reservations campaign.
     */
    private Long campaignId;

    /**
     * The {@link com.ideaas.services.domain.MapCampaign#getName name} of
     * the reservations campaign.
     */
    private String campaign;

    /**
     * The {@link com.ideaas.services.domain.MapCliente#getNombre name} of
     * the client of the reservations campaign.
     */
    private String client;

    /**
     * The {@link com.ideaas.services.domain.MapCliente#getId name} of
     * the client of the reservations campaign.
     */
    private Long clientId;

    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapElemento#getId advertising element}.
     */
    private Long elementId;

    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapEmpresa#getId company}.
     */
    private Long companyId;

    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapProvincia#getId province}.
     */
    private Long provinceId;

    /**
     * The {@link com.ideaas.services.domain.MapUbicacion location}
     * {@link com.ideaas.services.domain.MapLocalidad#getId city}
     */
    private Long cityId;

    /**
     * The {@link com.ideaas.services.domain.MapReservacion#getExhibir exhibir} of
     * the client of the reservations campaign.
     */
    private Boolean exhibir;

    /**
     * The {@link com.ideaas.services.domain.MapReservacion#getStartDate
     * reservations start date}.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * The {@link com.ideaas.services.domain.MapReservacion#getStartDate
     * reservations finish date}.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishDate;

    /**
     * The {@link com.ideaas.services.domain.MapReservacion#getStartDate
     * reservations start date}.
     */
    @DateTimeFormat(pattern = "dd-MM-yyyy")

    /**
     * This attribute is used when updating a circuit, so that the previous
     * value of the reservations start date can be kept in order to retrieve
     * all the reservations from the data source.
     */
    private LocalDate updatedStartDate;

    /**
     * This attribute is used when updating a circuit, so that the previous
     * value of the reservations finish date can be kept in order to retrieve
     * all the reservations from the data source.
     */
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate updatedFinishDate;

    /**
     * The status of the campaign, te status of the campaign is defined by the status
     * of all your reservations. f the campaign has at least one {@link ReservationState#NOT_CONFIRMED
     * unconfirmed reservation}, then it keeps that status, otherwise it keeps the status with the highest
     * {@link ReservationState#getCode code}
     */
    private ReservationState reservationState;

    /**
     * The reservation state {@link ReservationState#getCode code}
     */
    private Long reservationStateCode;

    /**
     * The total amount of all reservations, discounting {@link ReservationState
     * canceled reservations}
     */
    private Long reservationAmount;

    

    /**
     * A boolean that indicate if the campaign contain reservation that are {@link ReservationState
     * canceled}
     */
    private boolean hasCanceledReservations;

    public MapCircuitDTO(
            Long campaignId,
            String city,
            String company,
            String element,
            String province,
            String client,
            String campaign,
            LocalDate startDate,
            LocalDate finishDate,
            Long reservationtState,
            Long reservationAmount,
            boolean hasCanceledReservations,
            boolean exhibir,
            Long clientId,
            Long elementId,
            Long companyId,
            Long provinceId,
            Long cityId) {
        super(city, company, element, province);
        this.campaignId = campaignId;
        this.client = client;
        this.campaign = campaign;
        this.exhibir = exhibir;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.reservationState = ReservationState.of(reservationtState);
        this.reservationAmount = reservationAmount;
        this.clientId   = clientId;
        this.elementId  = elementId;
        this.companyId  = companyId;
        this.provinceId = provinceId;
        this.cityId     = cityId;
        this.hasCanceledReservations = hasCanceledReservations;
    }

    // public MapCircuitDTO(Long campaignId, String city, String company, String element, String province, String client, String campaign, LocalDate startDate, LocalDate finishDate, ReservationState reservationtState, Long reservationAmount, boolean hasCanceledReservations) {
    //     super(city, company, element, province);
    //     this.campaignId = campaignId;
    //     this.client = client;
    //     this.campaign = campaign;
    //     this.startDate = startDate;
    //     this.finishDate = finishDate;
    //     this.reservationState = reservationtState;
    //     this.reservationAmount = reservationAmount;
    //     this.hasCanceledReservations = hasCanceledReservations;
    // }

    public ReservationState getReservationState() {
        if(Objects.nonNull(reservationState)) return reservationState;
        if(Objects.nonNull(reservationStateCode)) return ReservationState.of(reservationStateCode);
        return null;
    }
}
