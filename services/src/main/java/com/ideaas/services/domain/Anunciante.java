package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "anunciantes")
@Data

public class Anunciante {

    @Id
    @Column(name = "idAnunciante")
    private Long id;

    @Column(name = "Descripcion")
    private String descripcion;

    public Anunciante() {
    }
}
