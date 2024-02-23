package com.ideaas.services.dao;

import com.ideaas.services.bean.MapCampaignDTO;
import com.ideaas.services.domain.MapCampaign;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapCampaignDao extends PagingAndSortingRepository<MapCampaign, Long> {
    /**
     * Finds every instance of MapCampaign in the db.
     *
     * @returns List of MapCampaign
     */
    @Query(name = "MapCampaign.findAllCampaigns", nativeQuery = true)
    List<MapCampaignDTO> findAllCampaigns(List<Integer> mapCompaniesIDs);


    @Query("SELECT mem.id FROM MapEmpresa mem " + 
                "INNER JOIN mem.mapClientes mcl,  " + 
                "MapCampaign mca " +
                "WHERE mca.mapClient.id = mcl.id AND mca.id = ?1"
    )
    Long findCompanyIdByCampaignId(Long campaignId);

    @Query(value = "" +
            "SELECT mca " +
            "FROM MapCampaign mca " //+
    )
    List<MapCampaign> findAll();

    /**
     * Finds a campaign by its id.
     *
     * @returns List of MapCampaign
     */
    @Query(name = "MapCampaign.findCampaignById",nativeQuery = true)
    MapCampaignDTO findCampaignById(Long id);

    @Query("SELECT CASE WHEN COUNT(r)>0 THEN TRUE ELSE FALSE END " +
            "FROM MapReservacion r " +
            "INNER JOIN r.mapCampaign c " +
            "WHERE r.reservationState = com.ideaas.services.enums.ReservationState.CANCELLED " +
            "AND c.id = ?1")
    boolean doesCampaignHaveCanceledReservations(Long id);

    @Query("SELECT mca.id FROM MapCampaign mca INNER JOIN mca.mapClient mcl INNER JOIN mcl.mapEmpresas me WHERE me.id IN ?2 AND mca.id IN ?1")
    List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds);
}
