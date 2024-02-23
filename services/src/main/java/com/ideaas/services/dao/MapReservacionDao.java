package com.ideaas.services.dao;

import com.ideaas.services.bean.MapCircuitDTO;
import com.ideaas.services.bean.MapReservationDTO;
import com.ideaas.services.domain.MapCampaign;
import com.ideaas.services.domain.MapReservacion;
import com.ideaas.services.enums.ReservationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The Map Reservation Repository.
 */
@Repository
public interface MapReservacionDao extends JpaRepository<MapReservacion, Long> {

   //#region circuits
   /**
    * This query selects all the circuits of a campaign by performing a group by on the
    * start and end date, company, element, province, location of a reservation. In
    * addition, select the number of confirmed reservations and not that this circuit
    * has, and the status of that circuit, which will only be confirmed if all the
    * reservations confirmed, ignoring the canceled ones.
    *
    * @param id            the id
    * @param mapCoppanyIDs the map coppany i ds
    * @return the list
    */
   @Query(name = "MapReservacion.findAllCircuitsByCampaignId", nativeQuery = true)
   List<MapCircuitDTO> findAllCircuitsByCampaignId(Long id, List<Integer> mapCoppanyIDs);

   /**
    * Delete a circuit, and all the reservations that share its data, selecting them equal to {@link #findAllCircuitsByCampaignId}
    *
    * @param circuitDTO the circuit dto
    */
   @Transactional
   @Modifying
   @Query(value = "DELETE FROM MapReservacion mr " +
           "WHERE " +
           "mr.mapCampaign.id = :#{#circuitDTO.campaignId} AND " +
           "mr.mapCampaign.mapClient.id = :#{#circuitDTO.clientId} AND " +
           "mr.startDate = :#{#circuitDTO.startDate} AND " +
           "mr.finishDate = :#{#circuitDTO.finishDate} AND " +
           "mr.mapUbicacion.mapEmpresa.id = :#{#circuitDTO.companyId} AND " +
           "mr.mapUbicacion.mapElemento.id = :#{#circuitDTO.elementId} AND " +
           "mr.mapUbicacion.mapProvincia.id = :#{#circuitDTO.provinceId} AND " +
           "mr.mapUbicacion.mapLocalidad.id = :#{#circuitDTO.cityId} ")
   void deleteCircuit(@Param("circuitDTO")MapCircuitDTO circuitDTO);

   //#endregion

   //#region mapReservation
   /**
    * Find all by reservation of the given locations by its ids.
    *
    * @param mapLocations the map locations
    * @return the list
    */
   @Query("SELECT mr FROM MapReservacion mr INNER JOIN mr.mapUbicacion mu WHERE mu.id IN (?1)")
   List<MapReservacion> findAllByLocationsIDs(List<Long> mapLocations);
   //#endregion

   //#region reservations dto
   /**
    * Select all reservation by a campaign id.
    *
    * @param id the id
    * @return the list
    */
   @Query("SELECT mr " +
           "FROM MapReservacion mr " +
           "INNER JOIN mr.mapCampaign mc " +
           "WHERE mc.id = ?1")
   List<MapReservacion> selectAllReservationByCampaignId(Long id);

   /**
    * Select all reservation dto by campaign id list.
    *
    * @param id the id
    * @return the list
    */
   @Query("SELECT new " +
           "   com.ideaas.services.bean.MapReservationDTO(" +
           "      mr.mapUbicacion.mapLocalidad.descripcion, " +
           "      mr.mapUbicacion.mapEmpresa.descripcion, " +
           "      mr.mapUbicacion.mapElemento.descripcion, " +
           "      mr.mapUbicacion.mapProvincia.descripcion, " +
           "      mr.mapUbicacion.idReferencia, " +
           "      mr.mapUbicacion.id, " +
           "      mr.mapUbicacion.direccion, " +
           "      mr.reservationState, " +
           "      mr.mapCampaign.name, " +
           "      mr.mapCampaign.mapClient.nombre, " +
           "      mr.startDate, " +
           "      mr.finishDate, " +
           "      mr.mapUbicacion.latitud, " +
           "      mr.mapUbicacion.longitud, " +
           "      mr.id, " +
           "      mr.mapUbicacion.cantidad," +
           "      mr.exhibir," +
           "      mr.ordenNumber" +
           "   ) " +
           "FROM MapReservacion mr " +
           "INNER JOIN mr.mapCampaign mc " +
           "WHERE mc.id = ?1")
   List<MapReservationDTO> selectAllReservationDTOByCampaignId(Long id);

   /**
    * Select all reservation by circuit list.
    *
    * @param circuitDTO the circuit dto
    * @return the list
    */
   @Query("SELECT mr " +
           "FROM MapReservacion mr " +
           "WHERE mr.mapCampaign.id = :#{#circuitDTO.campaignId} AND " +
           "mr.mapCampaign.mapClient.id = :#{#circuitDTO.clientId} AND " +
           "mr.startDate = :#{#circuitDTO.startDate} AND " +
           "mr.finishDate = :#{#circuitDTO.finishDate} AND " +
           "mr.mapUbicacion.mapEmpresa.id = :#{#circuitDTO.companyId} AND " +
           "mr.mapUbicacion.mapElemento.id = :#{#circuitDTO.elementId} AND " +
           "mr.mapUbicacion.mapProvincia.id = :#{#circuitDTO.provinceId} AND " +
           "mr.mapUbicacion.mapLocalidad.id = :#{#circuitDTO.cityId} ")
   List<MapReservacion> selectAllReservationByCircuit(@Param("circuitDTO") MapCircuitDTO circuitDTO);

   /**
    * Select all reservation dto by circuit list.
    *
    * @param circuitDTO    the circuit dto
    * @param mapEmpresaIds the map empresa ids
    * @return the list
    */
   @Query("SELECT new " +
           "   com.ideaas.services.bean.MapReservationDTO(" +
           "      mr.mapUbicacion.mapLocalidad.descripcion, " +
           "      mr.mapUbicacion.mapEmpresa.descripcion, " +
           "      mr.mapUbicacion.mapElemento.descripcion, " +
           "      mr.mapUbicacion.mapProvincia.descripcion, " +
           "      mr.mapUbicacion.idReferencia, " +
           "      mr.mapUbicacion.id, " +
           "      mr.mapUbicacion.direccion, " +
           "      mr.reservationState, " +
           "      mr.mapCampaign.name, " +
           "      mr.mapCampaign.mapClient.nombre, " +
           "      mr.startDate, " +
           "      mr.finishDate, " +
           "      mr.mapUbicacion.latitud, " +
           "      mr.mapUbicacion.longitud, " +
           "      mr.id, " +
           "      mr.mapUbicacion.cantidad," +
           "      mr.exhibir," +
           "      mr.ordenNumber" +
           "   ) " +
           "FROM MapReservacion mr " +
           "INNER JOIN mr.mapCampaign mca  " +
           "INNER JOIN mca.mapClient mcl   " +
           "INNER JOIN mr.mapUbicacion mu  " +
           "INNER JOIN mu.mapElemento mel  " +
           "INNER JOIN mu.mapLocalidad mlo " +
           "INNER JOIN mu.mapEmpresa mem   " +
           "INNER JOIN mu.mapProvincia mpr " +
           "WHERE (-1 IN (:mapEmpresaIds) OR mem.id IN (:mapEmpresaIds)) AND"  +
           "   mr.mapCampaign.id = :#{#circuitDTO.campaignId} AND " +
           "   mr.mapCampaign.mapClient.id = :#{#circuitDTO.clientId} AND " +
           "   mr.startDate = :#{#circuitDTO.startDate} AND " +
           "   mr.finishDate = :#{#circuitDTO.finishDate} AND " +
           "   mr.mapUbicacion.mapEmpresa.id = :#{#circuitDTO.companyId} AND " +
           "   mr.mapUbicacion.mapElemento.id = :#{#circuitDTO.elementId} AND " +
           "   mr.mapUbicacion.mapProvincia.id = :#{#circuitDTO.provinceId} AND " +
           "   mr.mapUbicacion.mapLocalidad.id = :#{#circuitDTO.cityId} ")
   List<MapReservationDTO> selectAllReservationDTOByCircuit(@Param("circuitDTO") MapCircuitDTO circuitDTO, @Param("mapEmpresaIds")  List<Integer> mapEmpresaIds);


   /**
    * Find reservation dto by id map reservation dto.
    *
    * @param id the id
    * @return the map reservation dto
    */
   @Query("SELECT new " +
           "   com.ideaas.services.bean.MapReservationDTO(" +
           "      mr.mapUbicacion.mapLocalidad.descripcion, " +
           "      mr.mapUbicacion.mapEmpresa.descripcion, " +
           "      mr.mapUbicacion.mapElemento.descripcion, " +
           "      mr.mapUbicacion.mapProvincia.descripcion, " +
           "      mr.mapUbicacion.idReferencia, " +
           "      mr.mapUbicacion.id, " +
           "      mr.mapUbicacion.direccion, " +
           "      mr.reservationState, " +
           "      mr.mapCampaign.name, " +
           "      mr.mapCampaign.mapClient.nombre, " +
           "      mr.startDate, " +
           "      mr.finishDate, " +
           "      mr.mapUbicacion.latitud, " +
           "      mr.mapUbicacion.longitud, " +
           "      mr.id, " +
           "      mr.mapUbicacion.cantidad," +
           "      mr.exhibir," +
           "      mr.ordenNumber" +  
           "   ) " +
           "FROM MapReservacion mr " +
           "INNER JOIN mr.mapCampaign mca  " +
           "INNER JOIN mca.mapClient mcl   " +
           "INNER JOIN mr.mapUbicacion mu  " +
           "INNER JOIN mu.mapElemento mel  " +
           "INNER JOIN mu.mapLocalidad mlo " +
           "INNER JOIN mu.mapEmpresa mem   " +
           "INNER JOIN mu.mapProvincia mpr " +
           "WHERE mr.id = ?1")
   MapReservationDTO findReservationDTOById(Long id);

   //#endregion

   //#region verifications
   /**
    * Select all the campaigns that have some reservation whose dates overlap with
    * the reservations that are passed by parameter.
    *
    * @param reservationIds          the reservation ids
    * @param reservationState        the reservation state
    * @param startDateDisplacedDays  the start date displaced days
    * @param finishDateDisplacedDays the finish date displaced days
    * @param includeSameReservations the include same reservations
    * @return the campaigns list
    */
   @Query(" SELECT DISTINCT mca  " +
           "FROM  MapReservacion mr1 " +
           "INNER JOIN mr1.mapUbicacion " +
           "INNER JOIN mr1.mapCampaign mca " +
           "WHERE mr1.reservationState = ?2 AND (?5 = true OR mr1.id NOT IN ?1) AND EXISTS(" +
           "   SELECT 1 " +
           "   FROM MapReservacion mr2 " +
           "   INNER JOIN mr1.mapUbicacion " +
           "   WHERE mr2.id IN ?1 AND mr2.mapUbicacion.id = mr1.mapUbicacion.id AND (" +
           "   function('ADDDATE',mr1.startDate , ?3) BETWEEN mr2.startDate AND mr2.finishDate OR " +
           "   function('ADDDATE',mr1.finishDate, ?4) BETWEEN mr2.startDate AND mr2.finishDate OR " +
           "   mr2.startDate  BETWEEN function('ADDDATE',mr1.startDate, ?3)  AND function('ADDDATE',mr1.finishDate, ?4) OR " +
           "   mr2.finishDate BETWEEN function('ADDDATE',mr1.startDate, ?3)  AND function('ADDDATE',mr1.finishDate, ?4)))")
   List<MapCampaign> selectOverlappedCampaignsReservationWithGivenReservationsAndState(List<Long> reservationIds, ReservationState reservationState, Long startDateDisplacedDays,  Long finishDateDisplacedDays, Boolean includeSameReservations);

   /**
    * This function checks if the group of reservations passed by parameter displaced in a number of days
    * overlaps with other confirmed reservations.
    *
    * @param reservationIds          The reservations ids to update
    * @param reservationState        The new reservation state
    * @param startDateDisplacedDays  the start date displaced days
    * @param finishDateDisplacedDays the finish date displaced days
    * @return a boolean indicating if they exist
    */
   @Query(" SELECT CASE WHEN (count(mr1) > 0) THEN true ELSE false END " +
           "FROM  MapReservacion mr1 " +
           "INNER JOIN mr1.mapUbicacion " +
           "INNER JOIN mr1.mapCampaign mca " +
           "WHERE mr1.reservationState = ?2 AND mr1.id NOT IN ?1 AND EXISTS(" +
           "   SELECT 1 " +
           "   FROM MapReservacion mr2 " +
           "   INNER JOIN mr1.mapUbicacion " +
           "   WHERE mr2.id IN ?1 AND mr2.mapUbicacion.id = mr1.mapUbicacion.id AND (" +
           "   function('ADDDATE',mr1.startDate , ?3) BETWEEN mr2.startDate AND mr2.finishDate OR " +
           "   function('ADDDATE',mr1.finishDate, ?4) BETWEEN mr2.startDate AND mr2.finishDate OR " +
           "   mr2.startDate  BETWEEN function('ADDDATE',mr1.startDate, ?3)  AND function('ADDDATE',mr1.finishDate, ?4) OR " +
           "   mr2.finishDate BETWEEN function('ADDDATE',mr1.startDate, ?3)  AND function('ADDDATE',mr1.finishDate, ?4)))")
   boolean existsOverlappedReservationWithGivenReservationAndStateDisplacedByDays(List<Long> reservationIds, ReservationState reservationState, Long startDateDisplacedDays,  Long finishDateDisplacedDays);

   /**
    * This function is used to check if there are reservations whose dates overlap with a given date range
    * and reservation status, in the same locations.
    *
    * @param locationsIds     the locations ids
    * @param startDate        the start date
    * @param endDate          the end date
    * @param reservationState the reservation state
    * @return a boolean indicating if they exist
    */
   @Query(" SELECT CASE WHEN (count(mc) > 0) THEN true ELSE false END " +
           "FROM MapUbicacion mu " +
           "INNER JOIN mu.mapReservaciones mr " +
           "INNER JOIN mr.mapCampaign mc " +
           "WHERE mu.id IN ?1 AND (" +
           "mr.startDate  BETWEEN ?2 AND ?3 OR " +
           "mr.finishDate BETWEEN ?2 AND ?3 OR " +
           "?2  BETWEEN mr.startDate AND mr.finishDate  OR " +
           "?3  BETWEEN mr.startDate AND mr.finishDate) AND " +
           "mr.reservationState = ?4")
   boolean existsReservationsInGivenLocationsBetweenDatesWithState(List<Long> locationsIds, LocalDate startDate, LocalDate endDate, ReservationState reservationState);

   /**
    * This function is used to verify if there are reservations in the same date range in the
    * same locations, with a given status.
    *
    * @param reservationsIds the reservations ids
    * @param locationsIds    the locations ids
    * @param campaignId      the campaign id
    * @param startDate       the start date
    * @param endDate         the end date
    * @return the optional
    */
   @Query(" SELECT DISTINCT mc " +
           "FROM MapUbicacion mu " +
           "INNER JOIN mu.mapReservaciones mr " +
           "INNER JOIN mr.mapCampaign mc " +
           "WHERE mr.id NOT IN (?1) AND mu.id IN ?2 AND (" +
           "mr.startDate  BETWEEN ?4 AND ?5 OR " +
           "mr.finishDate BETWEEN ?4 AND ?5 OR " +
           "?4  BETWEEN mr.startDate AND mr.finishDate  OR " +
           "?5  BETWEEN mr.startDate AND mr.finishDate) AND " +
           "mc.id = ?3")
   Optional<MapCampaign> selectCampaignByIdWithReservationsBetweenDates(List<Long> reservationsIds, List<Long> locationsIds, Long campaignId, LocalDate startDate, LocalDate endDate);

   /**
    * This function updates the status of overlapping reservations passed by parameter, and updates them with the new status
    * only if they do not overlap with other confirmed reservations other than those passed by parameter.
    *
    * @param reservationIds   The reservations ids to update
    * @param locationsIds     the locations ids
    * @param reservationState The new reservation state
    */
   @Transactional
   @Modifying
   @Query( value = "" +
           "UPDATE map_reservacion SET id_map_estado_reservacion=?3 " +
           "WHERE map_reservacion.idUbicacion IN (?2) AND ( " +
           "   EXISTS ( " +
           "      SELECT 1 " +
           "      FROM (SELECT * FROM map_reservacion) AS mr1 " +
           "      WHERE mr1.id_map_reservacion IN (?1) AND mr1.idUbicacion=map_reservacion.idUbicacion AND ( " +
           "         map_reservacion.fecha_desde BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         map_reservacion.fecha_hasta BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         mr1.fecha_desde BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta OR " +
           "         mr1.fecha_hasta BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta)) AND " +
           "   ((?3=3 AND EXISTS ( " +
           "      SELECT 1 " +
           "      FROM (SELECT * FROM map_reservacion) AS mr1 " +
           "      WHERE mr1.id_map_reservacion <> map_reservacion.id_map_reservacion AND mr1.idUbicacion=map_reservacion.idUbicacion AND ( " +
           "         map_reservacion.fecha_desde BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         map_reservacion.fecha_hasta BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         mr1.fecha_desde BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta OR " +
           "         mr1.fecha_hasta BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta) AND " +
           "         mr1.id_map_estado_reservacion=4)) OR " +
           "   (?3<>3 AND NOT EXISTS ( " +
           "      SELECT 1 " +
           "      FROM (SELECT * FROM map_reservacion) AS mr1 " +
           "      WHERE mr1.id_map_reservacion <> map_reservacion.id_map_reservacion AND mr1.idUbicacion=map_reservacion.idUbicacion AND ( " +
           "         map_reservacion.fecha_desde BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         map_reservacion.fecha_hasta BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         mr1.fecha_desde BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta OR " +
           "         mr1.fecha_hasta BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta) AND " +
           "         mr1.id_map_estado_reservacion=4))" +
           "   )) AND map_reservacion.id_map_reservacion NOT IN (?1)", nativeQuery = true)
   void updateAllReservationOverlappedWithGivenIfIsPosible(List<Long> reservationIds, List<Long> locationsIds, Long reservationState);

   /**
    * This function updates the status of overlapping reservations passed by parameter, and updates them with the new status
    * only if they do not overlap with other confirmed reservations other than those passed by parameter. This feature is useful
    * when you need to edit overlapping reservations without considering the first group of reservations.
    *
    * @param reservationIds   The reservations ids to must be overlapped
    * @param locationsIds     The locations ids to search overlapped reservations
    * @param reservationState The new reservation state
    */
   @Transactional
   @Modifying
   @Query( value = "" +
           "UPDATE map_reservacion SET id_map_estado_reservacion=?3 " +
           "WHERE map_reservacion.idUbicacion IN (?2) AND ( " +
           "   EXISTS ( " +
           "      SELECT 1 " +
           "      FROM (SELECT * FROM map_reservacion) AS mr1 " +
           "      WHERE mr1.id_map_reservacion IN (?1) AND mr1.idUbicacion=map_reservacion.idUbicacion AND (" +
           "         map_reservacion.fecha_desde BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         map_reservacion.fecha_hasta BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         mr1.fecha_desde BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta OR " +
           "         mr1.fecha_hasta BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta)) AND " +
           "   ((?3=3 AND EXISTS ( " +
           "      SELECT 1 " +
           "      FROM (SELECT * FROM map_reservacion) AS mr1 " +
           "      WHERE mr1.id_map_reservacion NOT IN (?1, map_reservacion.id_map_reservacion) AND mr1.idUbicacion=map_reservacion.idUbicacion AND (" +
           "         map_reservacion.fecha_desde BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         map_reservacion.fecha_hasta BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         mr1.fecha_desde BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta OR " +
           "         mr1.fecha_hasta BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta) AND " +
           "         mr1.id_map_estado_reservacion=4)) OR " +
           "   (?3<>3 AND (NOT EXISTS ( " +
           "      SELECT 1 " +
           "      FROM (SELECT * FROM map_reservacion) AS mr1 " +
           "      WHERE mr1.id_map_reservacion NOT IN (?1, map_reservacion.id_map_reservacion) AND mr1.idUbicacion=map_reservacion.idUbicacion AND (" +
           "         map_reservacion.fecha_desde BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         map_reservacion.fecha_hasta BETWEEN mr1.fecha_desde AND mr1.fecha_hasta OR " +
           "         mr1.fecha_desde BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta OR " +
           "         mr1.fecha_hasta BETWEEN map_reservacion.fecha_desde AND map_reservacion.fecha_hasta) AND " +
           "         mr1.id_map_estado_reservacion<>3)))" +
           "   )) AND map_reservacion.id_map_reservacion NOT IN (?1)", nativeQuery = true)
   void updateAllReservationsNotOverlappedWithGivenIfIsPosible(List<Long> reservationIds, List<Long> locationsIds, Long reservationState);

   /**
    * Does circuit have canceled reservations boolean.
    *
    * @param circuitDTO the circuit dto
    * @return the boolean
    */
      @Query("SELECT CASE WHEN COUNT(mr)>0 THEN TRUE ELSE FALSE END " +
              "FROM MapReservacion mr " +
              "WHERE mr.mapCampaign.id = :#{#circuitDTO.campaignId} AND " +
              "mr.mapCampaign.mapClient.id = :#{#circuitDTO.clientId} AND " +
              "mr.startDate = :#{#circuitDTO.startDate} AND " +
              "mr.finishDate = :#{#circuitDTO.finishDate} AND " +
              "mr.mapUbicacion.mapEmpresa.id = :#{#circuitDTO.companyId} AND " +
              "mr.mapUbicacion.mapElemento.id = :#{#circuitDTO.elementId} AND " +
              "mr.mapUbicacion.mapProvincia.id = :#{#circuitDTO.provinceId} AND " +
              "mr.mapUbicacion.mapLocalidad.id = :#{#circuitDTO.cityId} AND " +
              "mr.reservationState = com.ideaas.services.enums.ReservationState.CANCELLED")
   boolean doesCircuitHaveCanceledReservations(@Param("circuitDTO") MapCircuitDTO circuitDTO);

   /**
    * Filter entities ids tha belong to a group of {@link com.ideaas.services.domain.MapEmpresa map companies}.
    *
    * @param idsToFilter   the ids to filter
    * @param mapCompanyIds the map company ids
    * @return the list
    */
   @Query("SELECT mr.id FROM MapReservacion mr INNER JOIN mr.mapCampaign mca INNER JOIN mca.mapClient mcl INNER JOIN mcl.mapEmpresas me WHERE me.id IN ?2 AND mr.id IN ?1")
   List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds);

   @Transactional
   @Modifying
   @Query(value = "UPDATE map_reservacion SET exhibir =?2 WHERE map_reservacion.id_map_reservacion IN (?1)", nativeQuery = true)
   void setReservationExhibirByReservationsIds(List<Long> reservationIds, boolean b);

   @Modifying
   @Transactional
   @Query(value = "" +
           "update map_reservacion set exhibir=?1 " +
           "where id_map_reservacion in (" +
           "   select mapreserva1_.id_map_reservacion " +
           "   from  (SELECT * FROM map_reservacion) mapreserva1_ cross join " +
           "   map_campana mapcampaig2_ cross join " +
           "   map_ubicaciones mapubicaci3_ " +
           "   where mapreserva1_.id_map_campana=mapcampaig2_.id_map_campana " +
           "   and mapreserva1_.idUbicacion=mapubicaci3_.idUbicacion " +
           "   and mapreserva1_.id_map_campana = ?2 " +
           "   and mapcampaig2_.id_map_cliente = ?3 " +
           "   and mapreserva1_.fecha_desde    = ?4 " +
           "   and mapreserva1_.fecha_hasta    = ?5 " +
           "   and mapubicaci3_.idEmpresa      = ?6 " +
           "   and mapubicaci3_.idElemento     = ?7 " +
           "   and mapubicaci3_.idProvincia    = ?8 " +
           "   and mapubicaci3_.idLocalidad    = ?9)", nativeQuery = true)
   void setReservationExhibirByCircuit(boolean b, Long campaignId, Long clientId, LocalDate startDate, LocalDate finishDate, Long companyId, Long elementId, Long provinceId, Long cityId);

   @Transactional
   @Modifying
   @Query(value = "UPDATE map_reservacion SET exhibir =?2 WHERE map_reservacion.id_map_campana = ?1", nativeQuery = true)
   void setReservationExhibirByCampaignId(Long id, boolean b);
   //#endregion


   @Query("UPDATE MapReservacion e SET e.ordenNumber = :orderNumber WHERE e.id = :id")
   void updateOrderNumber(@Param("id") Long id, @Param("orderNumber") Long orderNumber);
}
