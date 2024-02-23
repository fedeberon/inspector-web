package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "map_ubicaciones_alturas")
@Data

public class MapUbicacionAltura {

    @Id
    @Column(name = "id_altura")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ponderador")
    private Double ponderador;

    @Column(name = "descripcion_larga")
    private String descripcionLarga;

    public MapUbicacionAltura() {
    }
}
