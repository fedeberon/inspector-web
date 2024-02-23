package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aud_agencias")
@Data

public class AudAgencia {

    @Id
    @Column(name = "IdAgencia")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    public AudAgencia() {
    }
}
