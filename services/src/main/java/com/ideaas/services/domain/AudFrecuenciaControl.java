package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aud_frecuenciacontroles")
@Data

public class AudFrecuenciaControl {

    @Id
    @Column(name = "idFrecuencia")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    public AudFrecuenciaControl() {
    }
}
