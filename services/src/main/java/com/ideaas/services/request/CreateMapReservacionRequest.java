package com.ideaas.services.request;

import com.ideaas.services.enums.ReservationState;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Create map reservacion request. This class contains all the information needed to
 * create {@link com.ideaas.services.domain.MapReservacion reservations} for a
 * {@link com.ideaas.services.domain.MapUbicacion location}.
 */
@Data
public class CreateMapReservacionRequest {
    /**
     * The reservations start date
     */
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    /**
     * The reservations finish date
     */
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate finishDate;

    /**
     * The {@link com.ideaas.services.domain.MapCliente customes} id that
     * is reserving th location
     */
    private Long mapClientId;

    /**
     * The {@link com.ideaas.services.domain.MapCampaign campaign} name. This attribute cannot be null if a
     * new campaign is being created to add the new reservations.
     *
     * @see #isNewCampaign
     */
    private String campaignName;

    /**
     * The id of the campaign to which the reservations are being added. This attribute will be null
     * if a new campaign is being created.
     *
     * @see #isNewCampaign
     */
    private Long campaignId;

    /**
     * The {@link ReservationState#code code status} to be assigned to newly created reservations.
     * This attribute is used to catch the information that the view sends.
     */
    private Long reservationStateCode;

    /**
     * The {@link ReservationState status} to be assigned to newly created reservations.
     * This attribute is used to map the information sent by the view
     */
    private ReservationState reservationState;

    /**
     * All the Ids of the reservation {@link com.ideaas.services.domain.MapUbicacion locations} separated by a comma.
     */
    private String mapLocationsIds;

    /*
     * This attribute indicates whether reservations should be assigned to a pre-existing campaign,
     * or a new campaign should be created and assigned to that campaign.This attribute is used to catch
     * the information that the view sends.
     */
    private Long isNewCampaignLong;

    /**
     * This attribute indicates whether reservations should be assigned to a pre-existing campaign,
     * or a new campaign should be created and assigned to that campaign. This attribute is used to
     * map the information sent by the view
     */
    private boolean isNewCampaign;

    /**
     * This attribute indicates the number of order
     */
    private Long ordenNumber;

    /**
     * Gets a boolean indicating if a new campaign should be created new campaign
     *
     * @see #isNewCampaign
     * @return the is new campaign
     */
    public boolean getIsNewCampaign() {
        return Objects.nonNull(isNewCampaignLong);
    }

    /**
     * Gets map locations ids list.
     *
     * @return the map locations ids list
     */
    public List<Long> getMapLocationsIdsList() {
        if(Objects.isNull(mapLocationsIds)||mapLocationsIds.isEmpty()) { return new ArrayList<>(); }
        return Arrays.stream(mapLocationsIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
    }

    /**
     * Gets reservation state.
     *
     * @return the reservation state
     */
    public ReservationState getReservationState() {
        if(Objects.nonNull(reservationState)) return reservationState;
        if(Objects.nonNull(reservationStateCode)) return ReservationState.of(reservationStateCode);
        return null;
    }
}
