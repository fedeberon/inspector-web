package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "map_provincias")
@Data

public class MapProvincia {
    @Id
    @Column(name = "idProvincia")
    @SequenceGenerator(name = "MapProvinciaSeqGen", sequenceName = "SEQ_MAP_PROVINCIAS", allocationSize = 1)
    @GeneratedValue(generator = "MapProvinciaSeqGen")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "evalua")
    private Long evalua;

    @Column(name = "bajaLogica")
    private Boolean bajaLogica;

    public MapProvincia() {
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

    public Long getEvalua() {
        return evalua;
    }

    public void setEvalua(Long evalua) {
        this.evalua = evalua;
    }

    public void setBajaLogica(Boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }
}
