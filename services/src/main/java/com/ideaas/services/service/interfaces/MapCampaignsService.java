package com.ideaas.services.service.interfaces;

import com.ideaas.services.bean.MapCampaignDTO;
import com.ideaas.services.domain.MapCampaign;

import java.util.List;

/**
 * The interface Map campaigns service.
 */
public interface MapCampaignsService extends MapCompanyFilterIdsService {

    //#region commons methods

    /**
     * Get map campaign.
     *
     * @param id the id
     * @return the map campaign
     */
    MapCampaign get(Long id);

    /**
     * Save map campaign.
     *
     * @param mapCampaing the map campaing
     * @return the map campaign
     */
    MapCampaign save(MapCampaign mapCampaing);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<MapCampaign> findAll();

    /**
     * Delete.
     *
     * @param mapCampaing the map campaing
     */
    void delete(MapCampaign mapCampaing);

    //#endregion


    MapCampaignDTO getCampaignDTOByCampaignId(Long id);

    List<MapCampaignDTO> findAllMapCampaignDTO();

    /**
     * Delete by id.
     *
     * @param idCampaign the id campaign
     */
    void deleteById(Long idCampaign);

    /**
     * Clone map campaign.
     *
     * @param mapCampaing the map campaing
     * @return the map campaign
     */
    MapCampaign clone(MapCampaign mapCampaing);

}
