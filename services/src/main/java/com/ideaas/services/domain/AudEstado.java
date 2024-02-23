package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aud_estados")
@Data

public class AudEstado {

    @Id
    @Column(name = "idEstado")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "sumaEn")
    private String sumaEn;

    public AudEstado() {
    }
}
