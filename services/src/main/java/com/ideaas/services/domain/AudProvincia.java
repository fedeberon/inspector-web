package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aud_provincias")
@Data

public class AudProvincia {

    @Id
    @Column(name = "idProvincia")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    public AudProvincia() {
    }
}
