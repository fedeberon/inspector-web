package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaas.services.bean.MapCampaignDTO;
import com.ideaas.services.enums.ReservationState;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


/**
 * A campaign is a way of organizing the reservations of locations by a client of a
 * public road company in the framework of an advertising campaign.
 *
 * @see MapCliente
 * @see MapReservacion
 * @see MapEmpresa
 * @see MapUbicacion
 */
@SqlResultSetMapping(
    name="findAllCampaignsResult",
    classes = {
        @ConstructorResult(
            targetClass= MapCampaignDTO.class,
            columns={
                @ColumnResult(name="campaignId", type=Long.class),
                @ColumnResult(name="campaignName", type=String.class),
                @ColumnResult(name="clientId", type=Long.class),
                @ColumnResult(name="clientName", type=String.class),
                @ColumnResult(name="startDate", type=LocalDate.class),
                @ColumnResult(name="finishDate", type=LocalDate.class),
                @ColumnResult(name="amountReservations", type=Long.class),
                @ColumnResult(name="reservationtState", type=Long.class),
                @ColumnResult(name="hasCanceledReservations", type=Boolean.class),
                @ColumnResult(name="exhibir", type=Boolean.class),
                @ColumnResult(name="orderNumber", type=Long.class),
                @ColumnResult(name="id", type=Long.class),
            }
        )
    })
@NamedNativeQueries(value = {
    @NamedNativeQuery(
        name="MapCampaign.findAllCampaigns",
        query=
            "SELECT " +
            "    mca.id_map_campana AS campaignId,  " +
            "    mca.nombre  AS campaignName,  " +
            "    mcl.id_map_cliente AS clientId, " +
            "    mcl.nombre AS clientName,  " +
            "    mre.orden_nro AS orderNumber, " +
            "    mre.id_map_reservacion AS id, " +
            "    (SELECT min(mre.fecha_desde) WHERE mre.id_map_estado_reservacion != 3) AS startDate, " +
            "    (SELECT max(mre.fecha_hasta) WHERE mre.id_map_estado_reservacion != 3) AS finishDate, " +
            "    coalesce((SELECT count(DISTINCT mre.id_map_reservacion) WHERE mre.id_map_estado_reservacion != 3), 0) AS amountReservations, " +
            "    coalesce((SELECT min(mre.id_map_estado_reservacion) WHERE mre.id_map_estado_reservacion != 3), 3) AS reservationtState, " +
            "    (SELECT CASE WHEN EXISTS (SELECT 1 FROM map_reservacion mre2 WHERE mre2.id_map_campana = mca.id_map_campana AND mre2.id_map_estado_reservacion = 3) THEN TRUE ELSE FALSE END) AS hasCanceledReservations," +
            "    coalesce((SELECT count(DISTINCT mre.id_map_reservacion) WHERE mre.exhibir = 0), 0) = 0 AS exhibir " +
            "FROM map_reservacion mre " +
            "INNER JOIN map_campana mca ON mre.id_map_campana=mca.id_map_campana  " +
            "INNER JOIN map_cliente mcl ON mcl.id_map_cliente=mca.id_map_cliente  " +
            "INNER JOIN map_empresa_map_cliente memc ON memc.id_map_cliente=mcl.id_map_cliente  " +
            "INNER JOIN map_empresas mem ON mem.idEmpresa=memc.idEmpresa " +
            "WHERE -1 IN (?1) OR mem.idEmpresa IN (?1) " +
            "GROUP BY mca.id_map_campana " +
            "ORDER BY startDate DESC",
        resultSetMapping ="findAllCampaignsResult"),
    @NamedNativeQuery(
        name="MapCampaign.findCampaignById",
        query=
            "SELECT " +
            "    mca.id_map_campana AS campaignId,  " +
            "    mca.nombre  AS campaignName,  " +
            "    mcl.id_map_cliente AS clientId, " +
            "    mcl.nombre AS clientName,  " +
            "    mre.orden_nro AS orderNumber, " +
            "    mre.id_map_reservacion AS id, " +
            "    (SELECT min(mre.fecha_desde) WHERE mre.id_map_estado_reservacion != 3) AS startDate, " +
            "    (SELECT max(mre.fecha_hasta) WHERE mre.id_map_estado_reservacion != 3) AS finishDate, " +
            "    coalesce((SELECT count(DISTINCT mre.id_map_reservacion) WHERE mre.id_map_estado_reservacion != 3), 0) AS amountReservations, " +
            "    coalesce((SELECT min(mre.id_map_estado_reservacion) WHERE mre.id_map_estado_reservacion != 3), 3) AS reservationtState, " +
            "    (SELECT CASE WHEN EXISTS (SELECT 1 FROM map_reservacion mre2 WHERE mre2.id_map_campana = mca.id_map_campana AND mre2.id_map_estado_reservacion = 3) THEN TRUE ELSE FALSE END) AS hasCanceledReservations," +
            "    coalesce((SELECT count(DISTINCT mre.id_map_reservacion) WHERE mre.exhibir = 0), 0) = 0 AS exhibir " +
            "FROM map_reservacion mre " +
            "INNER JOIN map_campana mca ON mre.id_map_campana=mca.id_map_campana  " +
            "INNER JOIN map_cliente mcl ON mcl.id_map_cliente=mca.id_map_cliente  " +
            "INNER JOIN map_empresa_map_cliente memc ON memc.id_map_cliente=mcl.id_map_cliente  " +
            "INNER JOIN map_empresas mem ON mem.idEmpresa=memc.idEmpresa " +
            "WHERE mca.id_map_campana = ?1 " +
            "GROUP BY mca.id_map_campana",
        resultSetMapping ="findAllCampaignsResult")
})
@Entity
@Table(name = "map_campana")
public class MapCampaign {

    @Id
    @SequenceGenerator(name = "MapCampanaSeqGen", sequenceName = "SEQ_MAP_CAMPANA", allocationSize = 1)
    @GeneratedValue(generator = "MapCampanaSeqGen")
    @Column(name = "id_map_campana")
    private Long id;

    @Column(name = "nombre")
    private String name;

    /**
     * The {@link MapCliente client} that is booking the locations.
     */
    @ManyToOne
    @JoinColumn(name = "id_map_cliente", referencedColumnName = "id_map_cliente")
    private MapCliente mapClient;

    /**
     * All the reservations that belong to the campaign.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "mapCampaign")
    private List<MapReservacion> mapReservations;

    public MapCampaign(Long id, String name, MapCliente mapCliente, LocalDate startDate, LocalDate finishDate, long amountReservations, ReservationState reservationtState) {
        this.id = id;
        this.name = name;
        this.mapClient = mapCliente;
    }

    /**
     * Instantiates a new Map campaing.
     *
     * @param name               the name
     * @param mapCliente         the map clientee
     */
    public MapCampaign(String name, MapCliente mapCliente) {
        this.name = name;
        this.mapClient = mapCliente;
    }


    /**
     * Instantiates a new Map campaing.
     */
    public MapCampaign() {}

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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets map cliente.
     *
     * @return the map cliente
     */
    public MapCliente getMapClient() {
        return mapClient;
    }

    /**
     * Sets map cliente.
     *
     * @param mapCliente the map cliente
     */
    public void setMapClient(MapCliente mapCliente) {
        this.mapClient = mapCliente;
    }

    public List<MapReservacion> getMapReservations() {
        return mapReservations;
    }

    public void setMapReservations(List<MapReservacion> mapReservations) {
        this.mapReservations = mapReservations;
    }

}
