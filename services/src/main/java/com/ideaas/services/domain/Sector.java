package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "sectores")
@Data

public class Sector {

    @Id
    @Column(name = "IDSector")
    private Long id;

    @Column(name = "Descripcion")
    private String descripcion;

    public Sector() {
    }
}
