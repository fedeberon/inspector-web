package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contratos_auditoria")
@Data

public class ContratoAuditoria {

    @Id
    @Column(name = "idContratoAud")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idContrato")
    private Contrato contrato;

    @Column(name = "idCampanna")
    private String idCampanna;

    public ContratoAuditoria() {
    }
}
