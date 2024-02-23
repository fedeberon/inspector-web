package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "map_audencia_planes")
@Data

public class MapAudienciaPlan {

    @Id
    @Column(name = "idMapAudienciaPlanes")
    private Long id;

    @Transient
    @Column(name = "idMapAudiencia")
    private Long idMapAudiencia;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public MapAudienciaPlan() {
    }
}
