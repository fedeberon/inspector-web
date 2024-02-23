package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contratos")
@Data

public class Contrato {

    @Id
    @Column(name = "idContrato")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "tipoContrato")
    private String tipoContrato;

    public Contrato() {
    }
}
