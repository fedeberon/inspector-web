package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "map_campanias")
@Data

public class MapCampania {

    @Id
    @Column(name = "idCampania")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "filtro")
    private String filtro;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public MapCampania() {
    }
}
