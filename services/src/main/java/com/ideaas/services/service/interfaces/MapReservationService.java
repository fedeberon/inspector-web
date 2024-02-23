package com.ideaas.services.service.interfaces;

import com.ideaas.services.bean.*;
import com.ideaas.services.domain.MapCampaign;
import com.ideaas.services.domain.MapReservacion;
import com.ideaas.services.request.CreateMapReservacionRequest;
import com.ideaas.services.request.ExportCircuitRequest;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;

/**
 * The interface Map reservation service. All CRUD operations that can be performed
 * in this service are restricted depending on the logged in user:
 * <ul>
 *     <li>{@link com.ideaas.services.enums.TipoUsuarioEnum#ROLE_ADMINISTRADOR admin type}:
 *     return all reservations</li>
 *     <li>{@link com.ideaas.services.enums.TipoUsuarioEnum#ROLE_ADMINISTRADOR_OOH OOH admin user}:
 *     returns all reservations if the circuit campaign belong to their
 *     {@link com.ideaas.services.domain.MapEmpresa companies}</li>
 * </ul>
 *
 * @todo remove all trows on methods and replace for something more semantic object, so the
 * controller can display better error messages
 */
public interface MapReservationService extends MapCompanyFilterIdsService {

    //#region common methods
    /**
     * Get a reservation by id.
     *
     * @param id the id
     * @return the map reservation
     */
    MapReservacion get(Long id);

    /**
     * Save map reservation.
     *
     * @param mapReservacion the map reservation
     * @return the map reservation
     */
    MapReservacion save(MapReservacion mapReservacion);

    /**
     * Find all map reservations.
     *
     * @return the list
     */
    List<MapReservacion> findAll();

    /**
     * Delete a map reservation.
     *
     * @param mapReservacion the map reservacion
     */
    void delete(MapReservacion mapReservacion);

    /**
     * Delete a list of map reservations by id.
     *
     * @param ids the map reservacion ids
     */
    void deleteAllByIds(List<Long> ids);
    //#endregion

    //#region campaign
    /**
     * Delete all reservations by campaign id.
     *
     * @param campaignID the campaign id
     */
    void deleteReservationsByCampaignId(Long campaignID);


    /**
     * Clone a campaign and all the reservations that belong to it, assigning a
     * new {@link MapReservacion#getStartDate start date}, a new {@link MapReservacion#getFinishDate
     * finish date} and a new {@link MapReservacion#getReservationState status}. This operation
     * can only be performed if it is possible, for more information see {@link MapReservacion}.
     *
     * @see MapReservacion
     * @param newMapCampaign the new map campaign
     * @return the map campaign
     */
    MapCampaign clone(MapCampaignDTO newMapCampaign);

    /**
     * Update a map campaign and all the reservations that belong to it, assigning a
     * new {@link MapReservacion#getStartDate start date}, a new {@link MapReservacion#getFinishDate
     * finish date} and a new {@link MapReservacion#getReservationState status}. This operation
     * can only be performed if it is possible, for more information see {@link MapReservacion}.
     *
     * @see MapReservacion
     * @param campaing the campaing
     * @return the map campaign
     */
    MapCampaign saveCampaign(MapCampaignDTO campaing);
    //#endregion

    //#region planning
    /**
     * This method creates a {@link MapReservacion resevation} for each of the given
     * {@link com.ideaas.services.domain.MapUbicacion locations}, based on the information provided by
     * {@link CreateMapReservacionRequest createMapReservacionRequest}.
     *
     * @see CreateMapReservacionRequest
     * @param createMapReservacionRequest the create map reservacion request
     * @return a boolean indicating the result of the operation
     */
    boolean saveMapReservations(CreateMapReservacionRequest createMapReservacionRequest);
    //#endregion

    //#region circuits
    /**
     * Find all circuits by a campaign id.
     *
     * @param id the id
     * @return the list
     */
    List<MapCircuitDTO> findAllCircuitsByCampaignId(Long id);

    /**
     * Save a circuit and transferring all its changes to the reservations that belong to it, assigning a
     * new {@link MapReservacion#getStartDate start date}, a new {@link MapReservacion#getFinishDate
     * finish date} and a new {@link MapReservacion#getReservationState status}. This operation
     * can only be performed if it is possible, for more information see {@link MapReservacion}.
     *
     * @see MapReservacion
     * @param circuitDTO the circuit dto
     * @return the saved map circuit dto
     */
    MapCircuitDTO saveCircuit(MapCircuitDTO circuitDTO);

    /**
     * Delete a circuit, and all reservations belonging to it.
     *
     * @param circuitDTO the circuit dto
     */
    void deleteCircuit(MapCircuitDTO circuitDTO);

    /**
     * Generates an Excel file with the requested circuits and returns it with an
     * array of bytes.
     *
     * @param exportCircuitRequest the export circuit request
     * @return byte [ ]
     * @throws IOException the io exception
     */
    byte[] exportCircuitsToExcel(ExportCircuitRequest exportCircuitRequest) throws IOException;

    /**
     * Generates an Excel file with the requested circuits and returns it with an
     * array of bytes.
     *
     * @param exportCircuitRequest the export circuit request
     * @return byte [ ]
     * @throws IOException                  the io exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws InterruptedException         the interrupted exception
     * @throws TransformerException         the transformer exception
     */
    byte[] exportCircuitsToPDF(ExportCircuitRequest exportCircuitRequest) throws IOException, ParserConfigurationException, InterruptedException, TransformerException;
    //#endregion

    //#region reservation
    /**
     * Find all the reservations by the ids of the {@link com.ideaas.services.domain.MapUbicacion locations}
     * they belong to.
     *
     * @param mapLocations the map locations
     * @return the list
     */
    List<MapReservacion> findAllByLocationsIDs(List<Long> mapLocations);
    //#endregion

    //#region dto reservations
    /**
     * Get a reservation by id.
     *
     * @param id the id
     * @return the reservation
     */
    MapReservationDTO getReservation(Long id);

    /**
     * Find all reservations dto tha belong to the given circuit.
     *
     * @param circuitDTO the circuit dto
     * @return the list
     */
    List<MapReservationDTO> findAllReservationsDTOByCircuit(MapCircuitDTO circuitDTO);

    /**
     * Save a map reservation dto.
     *
     * @param mapReservationDTO the map reservation dto
     * @return the map reservation dto
     */
    MapReservationDTO saveReservation(MapReservationDTO mapReservationDTO);

    /**
     * Delete reservation by id.
     *
     * @param id         the id
     * @param startDate  the start date
     * @param finishDate the finish date
     */
    void  deleteReservationById(Long id, LocalDate startDate, LocalDate finishDate);
    //#endregion

    //#region validations
    /**
     * Select all campaigns that have reservations whose dates overlap with the dates of the given
     * reservations, for the user to decide whether they can edit a campaign. This operation can
     * only be performed if it is possible, for more information see {@link MapReservacion}.
     *
     * @see VerifyReservationsCanUpdateResponse
     * @param campaign the campaign
     * @return the verify reservations can update response
     */
    VerifyReservationsCanUpdateResponse selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(MapCampaignDTO campaign);

    /**
     * Select all campaigns that have reservations whose dates overlap with the dates of the given
     * reservations, for the user to decide whether they can edit a circuit, and all its reservations.
     * This operation can only be performed if it is possible, for more information see {@link
     * MapReservacion}.
     *
     * @see VerifyReservationsCanUpdateResponse
     * @param circuitDTO the circuit dto
     * @return the verify reservations can update response
     */
    VerifyReservationsCanUpdateResponse selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(MapCircuitDTO circuitDTO);

    /**
     * Select all campaigns that have reservations whose dates overlap with the dates of the given
     * reservations, for the user to decide whether they can edit a reservation. This operation can
     * only be performed if it is possible, for more information see {@link MapReservacion}.
     *
     * @see VerifyReservationsCanUpdateResponse
     * @param reservationDTO the reservation dto
     * @return the verify reservations can update response
     */
    VerifyReservationsCanUpdateResponse selectCampaignsWithReservationsThatOverlapWithGivenReservationsAndStatus(MapReservationDTO reservationDTO);

    void setExhibido(List<Long> reservationIds);

    void setExhibido(MapCircuitDTO circuitDTO, Boolean exhibido);

    void setCampaignReservationExhibido(Long id, Boolean exhibido);
    //#endregion

    void setExhibido(SetMultipleExhibirCircuitsRequest circuitsDto);

    void setMultipleReservationExhibido(List<Long> reservationIdss, Boolean exhibir);

    byte[] exportCampaignCircuitsToExcel(ExportCircuitRequest exportCircuitRequest) throws IOException;

    byte[] exportCampaignCircuitsToPDF(ExportCircuitRequest exportCircuitRequest) throws IOException, InterruptedException, ParserConfigurationException, TransformerException;

    void updateOrderNumber(Long id, Long ordenNumber);
}