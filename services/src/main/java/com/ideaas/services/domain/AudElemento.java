package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aud_elementos")
@Data

public class AudElemento {

    @Id
    @Column(name = "idElemento")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "panios")
    private Long panios;

    public AudElemento() {
    }
}
