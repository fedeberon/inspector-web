package com.ideaas.services.domain;

import com.ideaas.services.bean.MapCampaignDTO;
import com.ideaas.services.bean.MapCircuitDTO;
import com.ideaas.services.enums.ReservationState;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * <p>This class represents a reservation for a defined time range, by an OOH
 * company {@link MapCliente client}, of one of the {@link MapUbicacion locations}
 * of an {@link MapEmpresa OOH company}.
 * <p>A reservation can go through different states depending on how it interacts
 * with other reservations whose dates overlap in the same location, see {@link
 * ReservationState}.
 *
 * @see MapCliente
 * @see MapUbicacion
 * @see MapEmpresa
 */
@SqlResultSetMapping(
    name="mapReservationToMapCircuitResultMapping",
    classes = {
        @ConstructorResult(
            targetClass= MapCircuitDTO.class,
            columns={
                @ColumnResult(name="campaignId", type=Long.class),
                @ColumnResult(name="city", type=String.class),
                @ColumnResult(name="company", type=String.class),
                @ColumnResult(name="element", type=String.class),
                @ColumnResult(name="province", type=String.class),
                @ColumnResult(name="client", type=String.class),
                @ColumnResult(name="campaign", type=String.class),
                @ColumnResult(name="startDate", type=LocalDate.class),
                @ColumnResult(name="finishDate", type=LocalDate.class),
                @ColumnResult(name="reservationtState", type=Long.class),
                @ColumnResult(name="amountReservations", type=Long.class),
                @ColumnResult(name="hasCanceledReservations", type=Boolean.class),
                @ColumnResult(name="exhibir", type=Boolean.class),
                @ColumnResult(name="clientId", type=Long.class),
                @ColumnResult(name="elementId", type=Long.class),
                @ColumnResult(name="companyId", type=Long.class),
                @ColumnResult(name="provinceId", type=Long.class),
                @ColumnResult(name="cityId", type=Long.class),
                
            }
        )
    })
@NamedNativeQuery(
    name="MapReservacion.findAllCircuitsByCampaignId",
    query=
        "SELECT " +
        "    mca.id_map_campana AS campaignId, " +
        "    mlo.descripcion AS city, " +
        "    mem.descripcion AS company, " +
        "    mel.descripcion AS element,  " +
        "    mpr.descripcion AS province, " +
        "    mcl.nombre AS client, " +
        "    mca.nombre AS campaign,  " +
        "    (SELECT min(mre.fecha_desde) WHERE mre.id_map_estado_reservacion != 3) AS startDate, " +
        "    (SELECT max(mre.fecha_hasta) WHERE mre.id_map_estado_reservacion != 3) AS finishDate, " +
        "    coalesce((SELECT min(mre.id_map_estado_reservacion) WHERE mre.id_map_estado_reservacion != 3), 3) AS reservationtState, " +
        "    coalesce((SELECT count(DISTINCT mre.id_map_reservacion) WHERE mre.id_map_estado_reservacion != 3), 0) AS amountReservations, " +
        "    CASE WHEN EXISTS (SELECT 1 WHERE mre.id_map_estado_reservacion = 3) " +
        "    THEN 'true' ELSE 'false' END AS hasCanceledReservations, " +
        "    mcl.id_map_cliente AS clientId, " +
        "    mel.idElemento AS elementId, " +
        "    mem.idEmpresa AS companyId, " +
        "    mpr.idProvincia AS provinceId, " +
        "    mlo.idLocalidad AS cityId," + 
        "    coalesce((SELECT count(DISTINCT mre.id_map_reservacion) WHERE mre.exhibir = 0), 0) = 0 AS exhibir " + 
        "FROM map_reservacion mre " +
        "INNER JOIN map_campana mca ON mre.id_map_campana=mca.id_map_campana  " +
        "INNER JOIN map_cliente mcl ON mcl.id_map_cliente=mca.id_map_cliente  " +
        "INNER JOIN map_empresa_map_cliente memc ON memc.id_map_cliente=mcl.id_map_cliente  " +
        "INNER JOIN map_empresas mem ON mem.idEmpresa=memc.idEmpresa " +
        "INNER JOIN map_ubicaciones mub ON mre.idUbicacion=mub.idUbicacion " +
        "INNER JOIN map_localidades mlo ON mub.idLocalidad=mlo.idLocalidad " +
        "INNER JOIN map_provincias mpr ON mub.idProvincia=mpr.idProvincia " +
        "INNER JOIN map_elementos mel ON mub.idElemento=mel.idElemento " +
        "WHERE mca.id_map_campana = ?1 AND (-1 IN (?2) OR mem.idEmpresa IN (?2)) " +
        "GROUP BY mca.id_map_campana, " +
        "         mcl.id_map_cliente, " +
        "         mre.fecha_desde, " +
        "         mre.fecha_hasta, " +
        "         mem.idEmpresa, " +
        "         mel.idElemento," +
        "         mpr.idProvincia," +
        "         mlo.idLocalidad",
    resultSetMapping ="mapReservationToMapCircuitResultMapping")
@Entity
@Table(name = "map_reservacion")
@ToString
public class MapReservacion {

    /**
     * The MapReservacion id
     */
    @Id
    @SequenceGenerator(name = "MapReservacionSeqGen", sequenceName = "SEQ_MAP_RESERVACION", allocationSize = 1)
    @GeneratedValue(generator = "MapReservacionSeqGen")
    @Column(name = "id_map_reservacion")
    private Long id;


    /**
     * The location that is being reserved.
     */
    @ManyToOne
    @JoinColumn(name = "idUbicacion", referencedColumnName = "idUbicacion")
    private MapUbicacion mapUbicacion;

    /**
     * The reservation campaign
     */
    @ManyToOne
    @JoinColumn(name = "id_map_campana", referencedColumnName = "id_map_campana")
    private MapCampaign mapCampaign;

    /**
     * The start date of the reservation. This date cannot be within the range
     * of dates in which a confirmed reservation is found
     */
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_desde")
    private LocalDate startDate;

    /**
     * The end date of the reservation, including the last day. This date cannot
     * be within the range of dates in which a confirmed reservation is found
     */
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_hasta")
    private LocalDate finishDate;

    @Column(name="exhibir")
    private Boolean exhibir = false;

    /**
     * A {@link  Boolean} indicating whether the reservation has been confirmed.
     */
    @Column(name = "id_map_estado_reservacion")
    private ReservationState reservationState;

    @Column(name = "orden_nro")
    private Long ordenNumber;

    /**
     * Instantiates a new Map reservacion.
     *
     * @param id               the id
     * @param mapCampaign      the map ubicacion
     * @param mapUbicacion     the map ubicacion
     * @param startDate        the start date
     * @param finishDate       the finish date
     * @param reservationState the reservation state
     */
    public MapReservacion(Long id, MapCampaign mapCampaign, MapUbicacion mapUbicacion, LocalDate startDate, LocalDate finishDate, ReservationState reservationState, Long ordenNumber) {
        this.id = id;
        this.mapCampaign = mapCampaign;
        this.mapUbicacion = mapUbicacion;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.reservationState = reservationState;
        this.ordenNumber = ordenNumber;
    }

    /**
     * Instantiates a new Map reservacion.
     */
    public MapReservacion() {}


    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets map ubicacion.
     *
     * @return the map ubicacion
     */
    public MapUbicacion getMapUbicacion() {
        return mapUbicacion;
    }

    /**
     * Sets map ubicacion.
     *
     * @param mapUbicacion the map ubicacion
     */
    public void setMapUbicacion(MapUbicacion mapUbicacion) {
        this.mapUbicacion = mapUbicacion;
    }

    /**
     * Gets fecha deste.
     *
     * @return the fecha deste
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets fecha deste.
     *
     * @param startDate the fecha deste
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets fecha hasta.
     *
     * @return the fecha hasta
     */
    public LocalDate getFinishDate() {
        return finishDate;
    }

    /**
     * Sets fecha hasta.
     *
     * @param finishDate the fecha hasta
     */
    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }


    /**
     * Gets reservation state.
     *
     * @return the reservation state
     */
    public ReservationState getReservationState() {
        return reservationState;
    }

    /**
     * Sets reservation state.
     *
     * @param reservationState the reservationt state
     */
    public void setReservationState(ReservationState reservationState) {
        this.reservationState = reservationState;
    }

    public MapCampaign getMapCampaign() {
        return mapCampaign;
    }

    public void setMapCampaign(MapCampaign mapCampaign) {
        this.mapCampaign = mapCampaign;
    }

    public void setExhibir(Boolean b) {
        this.exhibir= b;
    }

    public Long getOrdenNumber(){
        return ordenNumber;
    }

    public void setOrdenNumber(Long ordenNumber){
        this.ordenNumber = ordenNumber;
    }
}