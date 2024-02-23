package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "map_medios")
@Data

public class MapMedio {

    @Id
    @SequenceGenerator(name = "MapMedioSeqGen", sequenceName = "SEQ_MAP_MEDIOS", allocationSize = 1)
    @GeneratedValue(generator = "MapMedioSeqGen")
    @Column(name = "idMedio")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "bajaLogica")
    private Boolean bajaLogica;

    public MapMedio() {
    }

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

    public Boolean getBajaLogica() { return bajaLogica; }

    public void setBajaLogica(Boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }
}
