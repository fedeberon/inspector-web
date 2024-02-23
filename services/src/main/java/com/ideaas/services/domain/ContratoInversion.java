package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contratos_inversion")
@Data

public class ContratoInversion {

    @Id
    @Column(name = "idContratoInv")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idContrato")
    private Contrato contrato;

    @Column(name = "idRubro")
    private String rubro;

    @Column(name = "fechaDesde")
    private String fechaDesde;

    @Column(name = "fechaHasta")
    private String fechaHasta;

    @Column(name = "creatividades")
    private String creatividades;

    public ContratoInversion() {
    }
}
