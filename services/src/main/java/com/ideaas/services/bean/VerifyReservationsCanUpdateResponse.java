package com.ideaas.services.bean;

import com.ideaas.services.domain.MapCampaign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class is used to send to your view the campaigns whose dates overlap with the campaigns, circuits or
 * reservations that you are trying to modify.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyReservationsCanUpdateResponse {
    /**
     * The same campaign. This attribute contains the campaign being cloned, only if the new campaign
     * overlaps with the original.
     */
    MapCampaign sameCampaign;

    /**
     * All campaigns with confirmed reservations that overlap the campaign, circuits, or reservations
     * that are being modified.
     */
    List<MapCampaign> campaignWithConfirmedReservations;

    /**
     * All campaigns without confirmed reservations that overlap the campaign, circuits, or reservations
     * that are being modified.
     */
    List<MapCampaign> campaignWithReservations;
}
