package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "rubros")
@Data

public class Rubro {

    @Id
    @Column(name = "idRubros")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "IDSector")
    private Sector sector;

    public Rubro() {
    }
}
