package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "medios")
@Data

public class Medios {
    @Id
    @Column(name = "idmedio")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    public Medios() {
    }
}
