package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "map_compartir")
@Data
public class MapCompartir {

    @Id
    @Column(name = "idMapCompartir")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Column(name = "hash")
    private String hash;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "vto")
    private LocalDateTime vencimiento;

    @Column(name = "filtro")
    private String filtro;

    public MapCompartir() {
    }
}
