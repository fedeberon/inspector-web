package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contratos_mapping")
@Data

public class ContratoMapping {

    @Id
    @Column(name = "idContratoMap")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idContrato")
    private Contrato contrato;

    @Column(name = "idEVP")
    private Long idEVP;

    public ContratoMapping() {
    }
}
