package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aud_anunciantes")
@Data

public class AudAnunciante {

    @Id
    @Column(name = "idAnunciante")
    private Long id;

    @Column(name = "Descripcion")
    private String descripcion;

    public AudAnunciante() {
    }
}
