package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "map_pois")
@Data

public class MapPoi {

    @Id
    @Column(name = "idPoi")
    @SequenceGenerator(name = "MapPoiSeqGen", sequenceName = "SEQ_MAP_POIS", allocationSize = 1)
    @GeneratedValue(generator = "MapPoiSeqGen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idEntidad")
    private MapPoiEntidad mapPoiEntidad;

    @Column(name = "descripcion")
    private String descripcion;

    @Transient
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idProvincia")
    private MapProvincia mapProvincia;

    @Column(name = "geo_latitud")
    private BigDecimal geoLatitud;

    @Column(name = "geo_longitud")
    private BigDecimal geoLongitud;

    @Column(name = "icono")
    private String icono;

    @Column(name = "bajaLogica")
    private Boolean bajaLogica;

    public MapPoi() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MapPoiEntidad getMapPoiEntidad() {
        return mapPoiEntidad;
    }

    public void setMapPoiEntidad(MapPoiEntidad mapPoiEntidad) {
        this.mapPoiEntidad = mapPoiEntidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MapProvincia getMapProvincia() {
        return mapProvincia;
    }

    public void setMapProvincia(MapProvincia mapProvincia) {
        this.mapProvincia = mapProvincia;
    }

    public BigDecimal getGeoLatitud() {
        return geoLatitud;
    }

    public void setGeoLatitud(BigDecimal geoLatitud) {
        this.geoLatitud = geoLatitud;
    }

    public BigDecimal getGeoLongitud() {
        return geoLongitud;
    }

    public void setGeoLongitud(BigDecimal geoLongitud) {
        this.geoLongitud = geoLongitud;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Boolean getBajaLogica() {
        return bajaLogica;
    }

    public void setBajaLogica(Boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }
}
