package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "map_localidades")
@Data

public class MapLocalidad {

    @Id
    @Column (name = "idLocalidad")
    @SequenceGenerator(name = "MapLocalidadSeqGen", sequenceName = "SEQ_MAP_LOCALIDADES", allocationSize = 1)
    @GeneratedValue(generator = "MapLocalidadSeqGen")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn (name = "idProvincia")
    private MapProvincia mapProvincia;

    @Column(name = "evalua")
    private Long evalua;

    @Column(name = "bajaLogica")
    private Boolean bajaLogica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getEvalua() {
        return evalua;
    }

    public void setEvalua(Long evalua) {
        this.evalua = evalua;
    }

    public Boolean getBajaLogica() {
        return bajaLogica;
    }

    public void setBajaLogica(Boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }

    public MapLocalidad() {
    }
}
