package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "map_ubicaciones_visibilidades")
@Data

public class MapUbicacionVisibilidad {

    @Id
    @Column(name = "id_visibilidad")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ponderador")
    private Double ponderador;

    @Column(name = "desripcion_larga")
    private String descripcionLarga;

    public MapUbicacionVisibilidad() {
    }
}
